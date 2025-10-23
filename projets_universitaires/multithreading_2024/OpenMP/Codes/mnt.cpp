#include <iostream>
#include <fstream>
#include <stdlib.h>
#include "mnt.h"
#include "fonctions.h"
#include <vector>
#include <sstream>  
using namespace std;

mnt::mnt(string filename) {
   ifstream f(filename);
    if (!f.is_open()) {
        std::cerr << "Erreur : Impossible d'ouvrir le fichier " << filename << std::endl;
        exit(EXIT_FAILURE);
    }
    f >> nb_lignes;
    f >> nb_cols;
    int tmp;
    f >> tmp;
    f >> tmp;
    f >> tmp;
    if (nb_lignes <= 0 || nb_cols <= 0) {
        std::cerr << "Erreur : dimensions invalides du terrain : " 
                  << nb_lignes << " x " << nb_cols << std::endl;
        exit(EXIT_FAILURE);
    }
    f >> no_value;  

    this->terrain = new float[(nb_lignes + 2) * nb_cols];
    this->direction = new int[(nb_lignes + 2) * nb_cols];
    this->accumulation = new int[(nb_lignes + 2) * nb_cols];
    this->bassin = new int[nb_lignes * nb_cols];

    std::vector<int> file_data((nb_lignes + 1) * nb_cols);
    for (int i = 0; i < (nb_lignes) * nb_cols; i++) {
        f >> file_data[i]; 
    }

    #pragma omp parallel for collapse(2)
    for (int i = 0; i < nb_lignes; i++) {
        for (int j = 0; j < nb_cols; j++) {
            terrain[(i + 1) * nb_cols + j] = file_data[(i) * nb_cols + j];
        }
    }

    /*
    Cette partie est plus lente que le code implémenté entre la ligne 34 et 45 (quelques hypothèses mais on ignore la raison )
    for (int i = 0; i < nb_lignes; i++) {
        for (int j = 0; j < nb_cols; j++) {
            f >> terrain[(i+1)*nb_cols+j];
        }
    }*/

    f.close();
    
    // Utilisation de OpenMP Sections pour paralléliser l'initialisation des tableaux
    // en plus on ajoute les deux lignes additionelles de terrain
    #pragma omp parallel sections
    {
        #pragma omp section
        {
            // Initialiser bassin
            for (int i = 0; i < nb_lignes; i++) {
                for (int j = 0; j < nb_cols; j++) {
                    bassin[i * nb_cols + j] = -1;
                }
            }
        }

        #pragma omp section
        {
            for (int i = 0; i < nb_lignes; i++) {
                for (int j = 0; j < nb_cols; j++) {
                    accumulation[(i + 1) * nb_cols + j] = -1;
                }
            }
        }

        #pragma omp section
        {
            for (int j = 0; j < nb_cols; j++) {
                terrain[j] = no_value;
                terrain[(nb_lignes + 1) * nb_cols + j] = no_value;
            }            
        }

        #pragma omp section
        {
            // Initialiser direction
            for (int i = 0; i < nb_cols; i++) {
                direction[i] = 1;
                direction[(nb_lignes + 1) * nb_cols + i] = 5;
            }
        }

        #pragma omp section
        {
            // Initialiser accumulation
            for (int i = 0; i < nb_cols; i++) {
                accumulation[i] = 0;
                accumulation[(nb_lignes + 1) * nb_cols + i] = 0;
            }

        }

        /*
        Une fois que toutes les sections sont terminées, OpenMP synchronise automatiquement
        les threads pour s'assurer qu'ils ont tous terminé avant de continuer.
        */
    }

}




void mnt::affichageTerrain() {
    for (int i = 0; i < nb_lignes; i++) {
        for (int j = 0; j < nb_cols; j++)
            std::cout << this->terrain[(i+1) * nb_cols + j] << " ";
        std::cout << std::endl;
    }
}

void mnt::calculDirection() {
    // Traitement des bords (j == 0)
    #pragma omp parallel for
    for (int i = 0; i < nb_lignes; i++) {
        int x = i + 1;
        int x1 = x - 1;
        int x2 = x + 1;
        int y = 0;
        int y2 = y + 1;
        float val = terrain[x * nb_cols + y];

        if (val != no_value) {
            float tab_bord[5] = {
                terrain[x1 * nb_cols + y],
                terrain[x1 * nb_cols + y2],
                terrain[x * nb_cols + y2],
                terrain[x2 * nb_cols + y2],
                terrain[x2 * nb_cols + y]
            };
            direction[x * nb_cols + y] = f_bord1(val, tab_bord, no_value);
        }
    }

    // Traitement des bords (j == nb_cols - 1)
    #pragma omp parallel for
    for (int i = 0; i < nb_lignes; i++) {
        int x = i + 1;
        int x1 = x - 1;
        int x2 = x + 1;
        int y = nb_cols - 1;
        int y1 = y - 1;
        float val = terrain[x * nb_cols + y];

        if (val != no_value) {
            float tab_bord[5] = {
                terrain[x2 * nb_cols + y],
                terrain[x2 * nb_cols + y1],
                terrain[x * nb_cols + y1],
                terrain[x1 * nb_cols + y1],
                terrain[x1 * nb_cols + y]
            };
            direction[x * nb_cols + y] = f_bord2(val, tab_bord, no_value);
        }
    }

    // Traitement de l'intérieur (1 <= j < nb_cols - 1)
    #pragma omp parallel for collapse(2)
    for (int i = 0; i < nb_lignes; i++) {
        for (int j = 1; j < nb_cols - 1; j++) {
            int x = i + 1;
            int x1 = x - 1;
            int x2 = x + 1;
            int y = j;
            int y1 = y - 1;
            int y2 = y + 1;
            float val = terrain[x * nb_cols + y];

            if (val != no_value) {
                float tab[8] = {
                    terrain[x1 * nb_cols + y],
                    terrain[x1 * nb_cols + y2],
                    terrain[x * nb_cols + y2],
                    terrain[x2 * nb_cols + y2],
                    terrain[x2 * nb_cols + y],
                    terrain[x2 * nb_cols + y1],
                    terrain[x * nb_cols + y1],
                    terrain[x1 * nb_cols + y1]
                };
                direction[x * nb_cols + y] = f(val, tab, no_value);
            }
        }
    }
}


void mnt::affichageDirection() {
    std::vector<std::string> output(nb_lignes + 2);  // Un vecteur pour stocker les résultats de chaque thread

    #pragma omp parallel for
    for (int i = 0; i < nb_lignes + 2; i++) {
        // Formatage des résultats pour cette ligne dans le buffer
        std::ostringstream oss;

        for (int j = 0; j < nb_cols; j++) {
            oss << direction[i * nb_cols + j] << " ";
        }

        // Enregistrer le résultat dans le vecteur
        output[i] = oss.str();
    }

    // Affichage des résultats après que tous les threads aient terminé
    for (const auto& line : output) {
        std::cout << line << std::endl;
    }
}


void mnt::calculAccumulation() {
    int dir_bord1[5] = {5, 6, 7, 8, 1};
    int dir_bord2[5] = {1, 2, 3, 4, 5};
    int dir_general[8] = {5, 6, 7, 8, 1, 2, 3, 4};
    unsigned stop = 0;
    int nb_non_calculs = 0;
    int x, y;
    int x1, y1;
    int x2, y2;
    int tab[16];
    int tab_bord[10];

    while (stop == 0) {
        /*
        La variable nb_non_calculs est partagée entre les threads 
        elle est réduite avec reduction(+:nb_non_calculs). 
        Cela permet de somme les valeurs de cette variable à la fin de l'exécution parallèle.
        */
        #pragma omp parallel for private(x, y, x1, y1, x2, y2, tab, tab_bord) reduction(+:nb_non_calculs)
        for (int i = 0; i < nb_lignes; i++) {
            x = i + 1;
            x1 = x - 1;
            x2 = x + 1;
            for (int j = 0; j < nb_cols; j++) {
                y = j;
                y1 = y - 1;
                y2 = y + 1;
                int d = direction[x * nb_cols + y];
                if (d != no_dir_value) {
                    if (accumulation[x * nb_cols + y] == -1) {
                        if (j == 0) {
                            tab_bord[0] = accumulation[x1 * nb_cols + y];
                            tab_bord[1] = accumulation[x1 * nb_cols + y2];
                            tab_bord[2] = accumulation[x * nb_cols + y2];
                            tab_bord[3] = accumulation[x2 * nb_cols + y2];
                            tab_bord[4] = accumulation[x2 * nb_cols + y];
                            tab_bord[5] = direction[x1 * nb_cols + y];
                            tab_bord[6] = direction[x1 * nb_cols + y2];
                            tab_bord[7] = direction[x * nb_cols + y2];
                            tab_bord[8] = direction[x2 * nb_cols + y2];
                            tab_bord[9] = direction[x2 * nb_cols + y];
                            int res = f_acc(tab_bord, no_dir_value, dir_bord1, 5);
                            if (res != -1)
                                accumulation[x * nb_cols + j] = res;
                            else {
                                nb_non_calculs++;
                            }
                        } else if (j == (nb_cols - 1)) {
                            tab_bord[0] = accumulation[x2 * nb_cols + y];
                            tab_bord[1] = accumulation[x2 * nb_cols + y1];
                            tab_bord[2] = accumulation[x * nb_cols + y1];
                            tab_bord[3] = accumulation[x1 * nb_cols + y1];
                            tab_bord[4] = accumulation[x1 * nb_cols + y];
                            tab_bord[5] = direction[x2 * nb_cols + y];
                            tab_bord[6] = direction[x2 * nb_cols + y1];
                            tab_bord[7] = direction[x * nb_cols + y1];
                            tab_bord[8] = direction[x1 * nb_cols + y1];
                            tab_bord[9] = direction[x1 * nb_cols + y];
                            int res = f_acc(tab_bord, no_dir_value, dir_bord2, 5);
                            if (res != -1)
                                accumulation[x * nb_cols + j] = res;
                            else {
                                nb_non_calculs++;
                            }
                        } else {
                            tab[0] = accumulation[x1 * nb_cols + y];
                            tab[1] = accumulation[x1 * nb_cols + y2];
                            tab[2] = accumulation[x * nb_cols + y2];
                            tab[3] = accumulation[x2 * nb_cols + y2];
                            tab[4] = accumulation[x2 * nb_cols + y];
                            tab[5] = accumulation[x2 * nb_cols + y1];
                            tab[6] = accumulation[x * nb_cols + y1];
                            tab[7] = accumulation[x1 * nb_cols + y1];
                            tab[8] = direction[x1 * nb_cols + y];
                            tab[9] = direction[x1 * nb_cols + y2];
                            tab[10] = direction[x * nb_cols + y2];
                            tab[11] = direction[x2 * nb_cols + y2];
                            tab[12] = direction[x2 * nb_cols + y];
                            tab[13] = direction[x2 * nb_cols + y1];
                            tab[14] = direction[x * nb_cols + y1];
                            tab[15] = direction[x1 * nb_cols + y1];
                            int res = f_acc(tab, no_dir_value, dir_general, 8);
                            if (res != -1)
                                accumulation[x * nb_cols + j] = res;
                            else
                                nb_non_calculs++;
                        }
                    }
                }
            }
        }

        if (nb_non_calculs == 0)
            stop = 1;
        else
            nb_non_calculs = 0;
    }
}

void mnt::affichageAccumulation() {
    std::vector<std::string> output(nb_lignes);  // Un vecteur pour stocker les résultats de chaque ligne

    #pragma omp parallel for
    for (int i = 0; i < nb_lignes; i++) {
        // Formatage des résultats pour cette ligne dans le buffer
        std::ostringstream oss;
        // Ajouter chaque élément de la ligne dans le flux
        for (int j = 0; j < nb_cols; j++) {
            oss << this->accumulation[(i + 1) * nb_cols + j] << " ";
        }

        // Enregistrer le résultat dans le vecteur
        output[i] = oss.str();
    }

    // Affichage des résultats après que tous les threads aient terminé
    for (const auto& line : output) {
        std::cout << line << std::endl;
    }
}

void mnt::calculBassin() {
    int* ptr_direction = direction + nb_cols;

    #pragma omp parallel for collapse(2)
    for (int i = 0; i < nb_lignes; i++) {
        for (int j = 0; j < nb_cols; j++) {
            int dir_val = ptr_direction[i * nb_lignes + j];
            if (dir_val != no_dir_value) {
                if (bassin[i * nb_cols + j] == -1) {
                    bassin[i * nb_cols + j] = f_bassin(bassin, ptr_direction, nb_lignes, nb_cols, i, j, &num);
                }
            } else {
                bassin[i * nb_cols + j] = no_bassin_value;
            }
        }
    }
}

void mnt::affichageBassin() {
    std::vector<std::string> output(nb_lignes);  // Un vecteur pour stocker les résultats de chaque ligne

    #pragma omp parallel for
    for (int i = 0; i < nb_lignes; i++) {
        // Formatage des résultats pour cette ligne dans un ostringstream
        std::ostringstream oss;
        for (int j = 0; j < nb_cols; j++) {
            oss << bassin[i * nb_cols + j] << " ";
        }

        // Enregistrer le résultat dans le vecteur
        output[i] = oss.str();
    }

    // Affichage des résultats après que tous les threads aient terminé
    for (const auto& line : output) {
        std::cout << line << std::endl;
    }
}

mnt::~mnt() {
    free(this->terrain);
}
