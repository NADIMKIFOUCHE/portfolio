#include <iostream>
#include <limits>
#include <string>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <cstring> // for strlen
#include <arpa/inet.h> // for inet_ntoa
#include <cbor.h> // include CBOR library
#include <errno.h> // for errno
#include "Bdd.hpp" // Include trajectory database functionality
#include "Frechet.hpp"
#include "Point.hpp"
#include "Trajectoireclient.hpp"

// Function to check if a socket is still connected
bool isSocketConnected(int socket) {
    struct sockaddr_in addr;
    socklen_t addrLen = sizeof(addr);
    return getpeername(socket, (struct sockaddr*)&addr, &addrLen) == 0;
}

int main() {
    
    
    
    // Mise en place de la base de données
    Bdd bdd;
    std::string filename = "pigeons1.csv";
    bdd.lireFichier(filename);
    std::cout << " Base de Données créée - Nombre de trajectoires : " << bdd.nombreTrajectoires() << std::endl;
    
    // Création de la socket
    int serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocket == -1) {
        std::cerr << "Error creating socket\n";
        return 1;
    }

    // Liaison de la socket au port
    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    serverAddr.sin_port = htons(12345); // Port number
    
    if (bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) == -1) {
        std::cerr << "Error binding socket\n";
        return 1;
    }

    // Listen
    if (listen(serverSocket, 5) == -1) {
        std::cerr << "Error listening on socket\n";
        return 1;
    }

    std::cout << "Server started. Waiting for connections..." << std::endl;

    while (true) {
        // accepter les connexion entrantes
        struct sockaddr_in clientAddr;
        socklen_t clientAddrSize = sizeof(clientAddr);
        int clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientAddrSize);
        if (clientSocket == -1) {
            std::cerr << "Error accepting connection\n";
            continue;
        }

        // Afficher info client
        char clientInfo[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &(clientAddr.sin_addr), clientInfo, INET_ADDRSTRLEN);
        std::cout << "Client connected: " << clientInfo << ":" << ntohs(clientAddr.sin_port) << std::endl;
        
        // Creation du menu
        const char *menuOptions[] = {"1. Combien de trajectoires similaires pour un seuil donné", "2. Trajectoires similaires", "3. Trajectoire la plus similaire", "4. Quitter"};
        cbor_item_t *menu = cbor_new_definite_array(4);
        for (int i = 0; i < 4; ++i) {
            cbor_array_set(menu, i, cbor_build_string(menuOptions[i]));
        }

        // Coder le menu en cbor
        unsigned char *buffer;
        size_t buffer_size;
        buffer_size = cbor_serialize_alloc(menu, &buffer, &buffer_size);

        // Envoie du menu au client
        ssize_t bytesSent = send(clientSocket, buffer, buffer_size, 0);
        if (bytesSent == -1) {
            std::cerr << "Error sending menu to client\n";
        }

        // Clean up
        free(buffer);
        cbor_decref(&menu);
        
        
           // Reception du choix/nomfichier/seuil
        unsigned char choiceBuffer[1024]; 
        ssize_t choiceBytesReceived = recv(clientSocket, choiceBuffer, sizeof(choiceBuffer), 0);
        if (choiceBytesReceived == -1) {
            std::cerr << "Error receiving choice from client\n";
        } else {
            // Decoder le message du client 
            struct cbor_load_result result;
            cbor_item_t *receivedData = cbor_load(choiceBuffer, choiceBytesReceived, &result);
            if (receivedData == nullptr || cbor_typeof(receivedData) != CBOR_TYPE_ARRAY) {
                std::cerr << "Error: Invalid data received from client\n";
            } else {
                // Extraction du choix,nom du fichier,seuil.
                int clientChoice = cbor_get_int(cbor_array_get(receivedData, 0));
                const char *filename = (const char *)cbor_string_handle(cbor_array_get(receivedData, 1));
                double threshold = cbor_float_get_float(cbor_array_get(receivedData, 2));
                
                // Logique de l'application
                
                // Création de l'instance de Trajectoireclient
             Trajectoireclient trajectoireClient;

// Chargement du fichier de trajectoires spécifié par le client
            trajectoireClient.lireFichier(std::string(filename));
            trajectoireClient.afficherTrajectoire();


// Supposons que `bdd` est une instance de `Bdd` contenant toutes les trajectoires chargées,
// `trajectoireClient` est une instance de `Trajectoireclient` qui a chargé la trajectoire du fichier spécifié par le client,
// et `frechetCalculator` est une instance de `Frechet` qui peut calculer la distance de Fréchet.

Frechet frechetCalculator;
int count = 0;
switch (clientChoice) {
    case 1: {
        // Compter le nombre de trajectoires dont la distance de Fréchet par rapport à la trajectoire du client est inférieure au seuil
        
        for (const auto& entry : bdd.getLesTrajectoires()) {
            long double distance = frechetCalculator.frechet(trajectoireClient.get_trajectoire(), entry.second);
            if (distance <= threshold) {
                count++;
            }
        }
        std::cout << "Nombre de trajectoires similaires: " << count << std::endl;
        count=0;
        break;
    }
    case 2: {
       
        // Afficher les trajectoires dont la distance de Fréchet par rapport à la trajectoire du client est inférieure au seuil
        for (const auto& entry : bdd.getLesTrajectoires()) {
            long double distance = frechetCalculator.frechet(trajectoireClient.get_trajectoire(), entry.second);
            if (distance <= threshold) {
                count++;
                std::cout << "Trajectoire similaire (TrackID " << entry.first << "):" << std::endl;
                for (const auto& point : entry.second) {
                    std::cout << point << std::endl;
                }
            }
        }if(count==0){
                 std::cout << "Aucune trajectoire similaire" << std::endl;
         }
        count=0;
        break;
    }
    case 3: {
   

        // Trouver la trajectoire la plus similaire (distance de Fréchet minimale)
        long double minDistance = std::numeric_limits<long double>::max();
        const std::vector<Point>* mostSimilarTrajectory = nullptr;
        int mostSimilarTrackID = -1;
        for (const auto& entry : bdd.getLesTrajectoires()) {
            long double distance = frechetCalculator.frechet(trajectoireClient.get_trajectoire(), entry.second);
            if (distance < minDistance) {
                minDistance = distance;
                mostSimilarTrajectory = &entry.second;
                mostSimilarTrackID = entry.first;
            }
        }
        if (mostSimilarTrajectory != nullptr) {
            std::cout << "Trajectoire la plus similaire (TrackID " << mostSimilarTrackID << "):" << std::endl;
            for (const auto& point : *mostSimilarTrajectory) {
                std::cout << point << std::endl;
            }
        } else {
            std::cout << "Aucune trajectoire similaire trouvée." << std::endl;
            
        }
        count=0;
        break;
        
    }
    case 4 :{
        // Fermer la socket client
        close(clientSocket);
        break;
    
    }
    default:
        std::cerr << "Choix non valide reçu: " << clientChoice << std::endl;
        break;
}


   
      
    

   
  
        // Send a message to the client to wait for their request to be processed
        const char *waitMessage = "Please wait, your request is being processed.";
        ssize_t waitMessageSent = send(clientSocket, waitMessage, strlen(waitMessage), 0);
        if (waitMessageSent == -1) {
            std::cerr << "Error sending wait message to client\n";
        }
    
    
     /**/

        
    }

        
    

   
}
  

}

   // Fermer la socket du server ( on l'atteint jamais)
    close(serverSocket);

    return 0;
}
