#include <iostream>
#include <cstdlib>
#include <ctime>
#include <mpi.h>
#include "Game.h"
#include "string.h"

int main(int argc, char** argv) {
    // Initialisation de MPI
    size_t t = std::atoi(argv[1]);
    //pour fixer la grille, srand en commmentaire
    //srand(time(NULL));
    int stop_signal = 0;
    bool found = false; 
    MPI_Init(&argc, &argv);
    double start_time = MPI_Wtime();
    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Vérification du nombre de processus
    if (size < 2) {
        std::cerr << "Il faut au moins 2 processus pour faire fonctionner le programme!" << std::endl;
        MPI_Finalize();
        return 1;
    }

    // La taille de la grille doit être fournie comme argument
    if (argc < 2) {
        std::cerr << "Veuillez fournir la taille de la grille!" << std::endl;
        MPI_Finalize();
        return 1;
    }

    Game game(t);  // Création de l'objet Game
    

    if(rank==0){
        std::cout << "La grille initiale:" << std::endl;
        game.Print();
        std::cout << "-------------------------------" << std::endl;  
    }
    int somme_iteration;
    if(rank!=0){
        game.addFirstParent();
        while (game.pq.size() < size-1) { 
            game.addNewParent();
        }

        for (int j = 0; j < rank-1; j++){
            game.pq.pop();
        
        }
        if(rank != (size-1)){
            priority_queue<State> newpq;
            newpq.push(game.pq.top());
            game.pq = newpq;
        }
    }
    /*   // Envoyer un état à chaque processus
        for (int i = 1; i < size; i++) {
            // Envoi de f et g
            State state = game.pq.top();
            MPI_Send(&state.f, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&state.g, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

            // Envoi de la taille de la grille
            size_t grid_size = t * t + 3;
            MPI_Send(&grid_size, 1, MPI_UNSIGNED_LONG, i, 0, MPI_COMM_WORLD);

            // Envoi de la grille (char* grid)
            MPI_Send(state.grid, grid_size, MPI_CHAR, i, 0, MPI_COMM_WORLD);
*/
    int cpt = 1;
    int nb = 0;
    MPI_Request request;
    while (stop_signal==0) {
        if (rank == 0) {
    // Recevoir les grilles visitées des autres processus
    for (int i = 1; i < size; i++) {
        char* buff = (char*)malloc((t * t + 3) * sizeof(char));
        MPI_Irecv(buff, t * t + 3, MPI_CHAR, i, i, MPI_COMM_WORLD, &request);
        
        // Attendre que la réception soit terminée
        MPI_Wait(&request, MPI_STATUS_IGNORE);

        // Ajouter la grille visitée à la liste
        game.addVisited(buff);
        
        // Libérer la mémoire après l'utilisation
        free(buff);
    }

    // Construire la nouvelle liste des grilles à envoyer
    std::vector<char*> allVisitedGrids = game.visited;
    
    // Envoyer la nouvelle liste de grilles visitées aux autres processus
    for (int i = 1; i < size; i++) {
        int taille = allVisitedGrids.size();
        
        // Envoi de la taille
        MPI_Send(&taille, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        
        // Envoi des grilles
        for (int j = 0; j < taille; j++) {
            MPI_Send(allVisitedGrids[j], t * t + 3, MPI_CHAR, i, 0, MPI_COMM_WORLD);
        }
    }

} else {
   if(game.pq.size()!=0){
     // Envoyer la grille visitée actuelle au processus 0
    if (!game.inVisited(game.pq.top().grid)) {
        MPI_Isend(game.pq.top().grid, t * t + 3, MPI_CHAR, 0, rank, MPI_COMM_WORLD, &request);
        
        // Attendre que l'envoi soit terminé
        MPI_Wait(&request, MPI_STATUS_IGNORE);

        game.addNewParent();
        
        State currentState = game.pq.top();
        if (isFinal(currentState.grid, t)) {
            game.check = currentState.grid;
            stop_signal = 1;
            found = true;
        }
    }

    // Recevoir la mise à jour des grilles visitées du processus 0
    int taille;
    MPI_Recv(&taille, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

    for (int i = 0; i < taille; i++) {
        char* grilleVisitee = (char*)malloc((t * t + 3) * sizeof(char));
        MPI_Recv(grilleVisitee, t * t + 3, MPI_CHAR, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        // Ajouter à la liste des grilles visitées
        game.visited.push_back(grilleVisitee);
    }
   }
}

        // Diffuser l'état d'arrêt à tous les processus
        MPI_Bcast(&stop_signal, 1, MPI_INT, 0, MPI_COMM_WORLD);
}
       
    if (found) {
        std::cout << "Solution trouvée par le processus " << rank << " après " << nb << " itérations " << std::endl;
        print(game.check, t);
    } else {
        std::cout << "Solution non trouvée par le processus " << rank << " après " << nb << " itérations "<<std::endl;
    }

    MPI_Reduce(&nb,&somme_iteration,size,MPI_INT,MPI_SUM,0,MPI_COMM_WORLD);
    if (rank==0){
        std::cout << "Nombre itérations totale = " << somme_iteration << std::endl;
    }

    double end_time = MPI_Wtime();
    if (rank == 0) {
        std::cout << "Temps d'exécution total : " << (end_time - start_time) << " secondes" << std::endl;
    }
    MPI_Finalize();


    return 0;
}