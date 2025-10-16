#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <cbor.h>
#include <unistd.h> 

// Envoie de données au serveur en cbor
bool sendCBORData(int socket, cbor_item_t *data) {
    // Coder en cbor
    unsigned char *buffer;
    size_t buffer_size;
    buffer_size = cbor_serialize_alloc(data, &buffer, &buffer_size);

    // Envoie du message
    ssize_t bytesSent = send(socket, buffer, buffer_size, 0);

    // Free memory
    free(buffer);

    // Vérification des erreurs lors de l'envoi
    if (bytesSent == -1 || static_cast<size_t>(bytesSent) != buffer_size) {
        std::cerr << "Error sending data to server\n";
        return false;
    }

    return true;
}

int main() {
    // Creation de la socket client
    int clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == -1) {
        std::cerr << "Error creating socket\n";
        return 1;
    }

    // Déclaration des infos du serveur
    struct sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(12345);
    serverAddr.sin_addr.s_addr = inet_addr("172.18.0.1");

    // Se connecter au serveur
    if (connect(clientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) == -1) {
        std::cerr << "Error connecting to server\n";
        close(clientSocket);
        return 1;
    }

    std::cout << "Connected to server. Waiting for menu..." << std::endl;

    // Reception du menu du serveur
    unsigned char buffer[1024];
    ssize_t bytesReceived = recv(clientSocket, buffer, sizeof(buffer), 0);
    if (bytesReceived == -1) {
        std::cerr << "Error receiving menu from server\n";
        close(clientSocket);
        return 1;
    }

    
    cbor_item_t *menu;
    struct cbor_load_result result;
    menu = cbor_load(buffer, bytesReceived, &result);
    if (menu == nullptr) {
        std::cerr << "Error loading CBOR menu\n";
        close(clientSocket);
        return 1;
    }

    // Affichage du menu
    if (cbor_typeof(menu) != CBOR_TYPE_ARRAY) {
        std::cerr << "Error: Received data is not an array\n";
        cbor_decref(&menu);
        close(clientSocket);
        return 1;
    }

    std::cout << "Menu received from server:\n";
    size_t numOptions = cbor_array_size(menu);
    for (size_t i = 0; i < numOptions; ++i) {
        cbor_item_t *option = cbor_array_get(menu, i);
        if (cbor_typeof(option) != CBOR_TYPE_STRING) {
            std::cerr << "Error: Menu option is not a string\n";
            cbor_decref(&menu);
            close(clientSocket);
            return 1;
        }
        unsigned char *optionData = cbor_string_handle(option);
        size_t optionSize = cbor_string_length(option);
        std::cout << std::string(optionData, optionData + optionSize) << std::endl;
    }

    while (true) {
        // Demande du choix du client
        int choice;
        std::cout << "Enter your choice (1, 2, 3 or 4): ";
        std::cin >> choice;

        // Validation du choix
        if (choice < 1 || choice > 4) {
            std::cerr << "Invalid choice. Please enter 1, 2, 3 or 4 .\n";
            continue;
        }
        if(choice==4){
             break;
         
         }

        // Demande du nom de fichier + seuil
        std::string filename;
        double threshold;
        std::cout << "Enter nom du fichier: ";
        std::cin >> filename;
        std::cout << "Enter seuil: ";
        std::cin >> threshold;

        // Preparer les choix comme du CBOR data
        cbor_item_t *userDataArray = cbor_new_definite_array(3);
        cbor_array_set(userDataArray, 0, cbor_build_uint8(choice));
        cbor_array_set(userDataArray, 1, cbor_build_string(filename.c_str()));
        cbor_array_set(userDataArray, 2, cbor_build_float8(threshold));

        // Envoi des details de l'utilisateur au serveur
        if (!sendCBORData(clientSocket, userDataArray)) {
            cbor_decref(&menu);
            cbor_decref(&userDataArray);
            close(clientSocket);
            return 1;
        }

        // Clean up
        cbor_decref(&userDataArray);
        
}        
    
    // Clean up
    cbor_decref(&menu);
    close(clientSocket);

    return 0;

}

