#include <iostream>
#include <math.h>
#include <queue>
#include <vector>
#include <cstring>
#include "Grid.h"
#include "Game.h"
#include <mpi.h>

using namespace std;

// Constructeur de la classe Game
Game::Game(size_t _t) {
    t = _t;
    taquin = (char*)malloc((t * t + 3) * sizeof(char)); // allocation mémoire pour la grille
    initGrid(taquin, t);  // Initialisation de la grille avec une configuration solvable
}

std::vector<State> Game::extractAllStates() {
    std::vector<State> allStates;

    // Extraire tous les états de la priority queue
    while (!pq.empty()) {
        State state = pq.top();
        pq.pop();
        allStates.push_back(state);  // Ajouter l'état extrait au vecteur
    }

    return allStates;  // Retourner le vecteur avec tous les états extraits
}

// Ajouter un nouvel état dans la priority queue
void Game::addVisited(char* child) {
    char* newVisited = (char*)malloc((t * t + 3) * sizeof(char));
    memcpy(newVisited, child, t * t + 3);
    visited.push_back(newVisited);
}

// Vérifier si un état a déjà été visité
bool Game::inVisited(char* child) {
    for (int i = 0; i < visited.size(); i++) {
        // Utilisation de memcmp pour comparer les grilles
        if (memcmp(visited[i], child, t * t * sizeof(char)) == 0) {
            return true;
        }
    }
    return false;
}

// Ajouter le premier parent à la priority queue
void Game::addFirstParent() {
    addVisited(taquin);  // Ajouter la grille initiale dans la liste des visités
    State initialState = {0, 0, taquin};  // f(n) = 0, g(n) = 0 pour la grille initiale
    pq.push(initialState);  // Ajouter l'état initial dans la priority queue
}

// Calculer la valeur f(n) et ajouter un nouvel état à la priority queue
void Game::addNewParent() {
    // Pop l'état actuel de la priority queue
    State currentState = pq.top();
    pq.pop();
    char* parent = currentState.grid;
    addVisited(parent);
    
    // Calculer g(n) pour chaque enfant et les ajouter à la priority queue
    int g = currentState.g + 1;
    
    // Générer les 4 voisins possibles
    char* childDown = (char*)malloc((t * t + 3) * sizeof(char));
    memcpy(childDown, parent, (t * t + 3) * sizeof(char));
    bool valid = moveFromDown(childDown, t);
    if (valid && !inVisited(childDown)) {
        int h = distance(childDown, t);
        pq.push({g + h, g, childDown});
    }
    
    char* childUp = (char*)malloc((t * t + 3) * sizeof(char));
    memcpy(childUp, parent, (t * t + 3) * sizeof(char));
    valid = moveFromUp(childUp, t);
    if (valid && !inVisited(childUp)) {
        int h = distance(childUp, t);
        pq.push({g + h, g, childUp});
    }

    char* childLeft = (char*)malloc((t * t + 3) * sizeof(char));
    memcpy(childLeft, parent, (t * t + 3) * sizeof(char));
    valid = moveFromLeft(childLeft, t);
    if (valid && !inVisited(childLeft)) {
        int h = distance(childLeft, t);
        pq.push({g + h, g, childLeft});
    }

    char* childRight = (char*)malloc((t * t + 3) * sizeof(char));
    memcpy(childRight, parent, (t * t + 3) * sizeof(char));
    valid = moveFromRight(childRight, t);
    if (valid && !inVisited(childRight)) {
        int h = distance(childRight, t);
        pq.push({g + h, g, childRight});
    }

    
}

// Fonction principale de l'algorithme A*
int Game::iteration(int rank,int stop_signal) {
    int cpt = 0;
    bool found = false;
    addFirstParent();
    
    if (isFinal(taquin, t)) {
            check = taquin;
            found = true;
        }
        

    while (!found) {
        //cout << rank <<" entre" <<  endl;
        cout << "SIGNAL=" << stop_signal <<  endl;
        if(stop_signal==1)
        {
            cout << "Le processus " << rank <<" arrete son travail avec"<< cpt <<  endl;
            found = !found;
            return cpt;
        }
        cpt++;
        if (cpt > 10000) {
            cout << "Solution non trouvée après 10000 itérations!" << endl;
            return cpt;
        }

        addNewParent();
        State currentState = pq.top();
       // pq.pop();
        
        // Si l'état actuel est la solution, on termine
        if (isFinal(currentState.grid, t)) {
            check = currentState.grid;
            found = true;
            stop_signal = 1;  // Mettre à jour le signal de fin
            MPI_Bcast(&stop_signal, 1, MPI_INT, rank, MPI_COMM_WORLD);
        }
    }
    return cpt;
}

// Destructor
Game::~Game() {
    while (!pq.empty()) {
        State currentState = pq.top();
        pq.pop();
        free(currentState.grid);
    }
    for (int i = 0; i < visited.size(); i++) {
        free(visited[i]);
    }
    if (check != NULL) {
        free(check);
    }
}

// Fonction pour afficher la grille (optionnel, à des fins de débogage)
void Game::Print() {
    print(taquin,t);
}

// Afficher la liste des grilles visitées (optionnel, pour débogage)
void Game::printVisited() {
    for (int i = 0; i < visited.size(); i++) {
        for (int j = 0; j < t * t; j++) {
            cout << visited[i][j] << " ";
        }
        cout << endl;
    }
}

// Afficher l'espace de recherche (optionnel)
void Game::printSearchSpace() {
    cout << "Espace de recherche (" << pq.size() << " éléments):" << endl;
    while (!pq.empty()) {
        State currentState = pq.top();
        pq.pop();
        for (int i = 0; i < t * t; i++) {
            cout << currentState.grid[i] << " ";
        }
        cout << endl;
    }
}
