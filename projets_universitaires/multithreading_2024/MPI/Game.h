#ifndef SEQUENTIEL_GAME_H
#define SEQUENTIEL_GAME_H
#include <vector>
#include <queue>  // pour priority_queue
#include "Grid.h"

using namespace std;

class Game {
private:
    size_t t; // La grille est de taille t x t
    char* taquin; // La grille initiale à résoudre : 1er parent
    std::vector<char*> searchspace; // Espace de recherche en cours

public:
    std::priority_queue<struct State> pq;
    std::vector<char*> visited;

    char* check = NULL; // Solution trouvée, si applicable
    Game(size_t _t); // Constructeur avec en particulier la grille initiale
    bool inVisited(char* child); // Test si une grille enfant est dans la liste des grilles visitées
    void addVisited(char* child); // Empiler la grille
    int iteration(int rank,int stop_signal);  // La fonction principale pour résoudre le Taquin
    void Print();
    void printVisited();
    void printSearchSpace();
    vector<State> extractAllStates();
    
    // Destructor
    ~Game();

    void addFirstParent();
    void addNewParent();
    void popParents();
};

#endif //SEQUENTIEL_GAME_H
