//
// Created by sophie on 07/10/24.
//

#ifndef CODES_GRID_H
#define CODES_GRID_H

#include <stdlib.h>

struct State {
    int f;
    int g;
    char* grid;
    
    bool operator<(const State& other) const {
        return f > other.f; // comparaison pour priority_queue
    }
};

// Initialisation de la grille : construction d'une grille solvable
void initGrid(char* g, size_t t);

// Vérifie si la grille est un parent
bool isParent(char *g, size_t t);

// Pour transformer un enfant en parent
void makeParent(char *g, size_t t);

// Renvoie le numéro de la case vide (entre 0 et t^2-1)
int emptyCase(char *g, size_t t);

// Remplir la case vide par le déplacement de la case au dessus si possible
// Renvoie false sinon
bool moveFromUp(char *g, size_t t);

// Remplir la case vide par le déplacement de la case en dessous si possible
// Renvoie false sinon
bool moveFromDown(char *g, size_t t);

// Remplir la case vide par le déplacement de la case à gauche  si possible
// Renvoie false sinon
bool moveFromLeft(char *g, size_t t);

// Remplir la case vide par le déplacement de la case à droite si possible
// Renvoie false sinon
bool moveFromRight(char *g, size_t t);

// Calcul d'une distance par rapport à la grille solution
//somme des déplacements pour positionner la case à la bonne place
int distance(char *g, size_t t);

// Pour vérifier si la grille est la grille solution
bool isFinal(char *g, size_t t);

void print(char *g, size_t t);




#endif //CODES_GRILLE_H
