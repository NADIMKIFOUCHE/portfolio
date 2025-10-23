#include <iostream>
#include <chrono>  //La bibliothèque chrono pour mesurer le temps
#include "mnt.h"

int main(int argc, char* argv[]) {
    // Début du chronomètre
    auto start = std::chrono::high_resolution_clock::now();

   if (argc < 3) {
        std::cerr << "Usage: program <nom de fichier> <num_threads>" << std::endl;
        return 1;
    }

    // Convertir argv[2] (string) en int
    int num_threads = std::stoi(argv[2]);

    // Définir le nombre de threads OpenMP
    omp_set_num_threads(num_threads);

    std::string filename = argv[1];
    mnt M1(filename);
    
    std::cout << "le terrain" << std::endl;
    //M1.affichageTerrain();
    
    M1.calculDirection();
    std::cout << "les directions" << std::endl;
    //M1.affichageDirection();

    M1.calculAccumulation();
    std::cout << "l'accumulation" << std::endl;
    //M1.affichageAccumulation();

    M1.calculBassin();
    std::cout << "le bassin" << std::endl;
    //M1.affichageBassin();

    // Fin du chronomètre
    auto end = std::chrono::high_resolution_clock::now();

    // Calcul et affichage du temps d'exécution
    std::chrono::duration<double> duration = end - start;
    std::cout << "Temps d'exécution : " << duration.count() << " secondes." << std::endl;

    return 0;
}
