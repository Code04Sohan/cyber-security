// Write a C program that accepts a text message from the user and performs encryption and decryption using a user-defined key. 
// The program should use a switch-case structure to allow the user to select between encryption and decryption operations. 
// Display the enrollment no along with the output. 
// Additionally, if the user attempts to decrypt the message using a key different from the used during encryption, 
// the program should display an appropriate error message.

#include <stdio.h>
#include <string.h>

// ================= CONFIG AREA =================
#define ENROLLMENT_NO "511116165449"
// ===============================================

int main() {
    char message[256];
    int choice;
    int key;
    int stored_key = -1; 
    int is_encrypted = 0;
    char enrollment_no[] = "511116165449"; 

    // Get initial message
    printf("Enter the text message: ");
    fgets(message, sizeof(message), stdin);
    message[strcspn(message, "\n")] = '\0'; // Remove newline character

    while(1) {
        printf("\n--- Encryption/Decryption Menu ---\n");
        printf("1. Encrypt Message\n");
        printf("2. Decrypt Message\n");
        printf("3. Exit\n");
        printf("Select an option: ");
        scanf("%d", &choice);

        switch(choice) {
            case 1:
                if (is_encrypted) {
                    printf("Message is already encrypted.\n");
                    break;
                }
                
                printf("Enter encryption key (integer): ");
                scanf("%d", &key);
                stored_key = key; // Save key to verify during decryption

                for(int i = 0; message[i] != '\0'; ++i) {
                    message[i] = message[i] + key;
                }
                
                is_encrypted = 1;
                printf("\nEnrollment No: %s\n", enrollment_no);
                printf("Encrypted message: %s\n", message);
                break;

            case 2:
                if (!is_encrypted) {
                    printf("Message is not currently encrypted.\n");
                    break;
                }
                
                printf("Enter decryption key (integer): ");
                scanf("%d", &key);

                // Verify the key matches the one used for encryption
                if(key != stored_key) {
                    printf("\nEnrollment No: %s\n", enrollment_no);
                    printf("Error: Incorrect decryption key! Access Denied.\n");
                } else {
                    for(int i = 0; message[i] != '\0'; ++i) {
                        message[i] = message[i] - key;
                    }
                    
                    is_encrypted = 0;
                    stored_key = -1; // Reset stored key
                    printf("\nEnrollment No: %s\n", enrollment_no);
                    printf("Decrypted message: %s\n", message);
                }
                break;

            case 3:
                printf("Exiting program...\n");
                return 0;

            default:
                printf("Invalid choice. Please select 1, 2, or 3.\n");
        }
    }

    return 0;
}