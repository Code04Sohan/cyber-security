#include <stdio.h>
#include <stdlib.h>

// ================= CONFIG AREA =================
#define ENROLLMENT_NO "1515161516515"
#define INPUT_FILE "input.txt"    
#define ENCRYPT_FILE "encrypted.txt"
#define DECRYPT_FILE "decrypted.txt"
// ===============================================

int main() {
    int choice;
    int key;
    int stored_key = -1; 
    FILE *fin, *fout;
    char ch;

    printf("--- Enrollment No: %s ---\n", ENROLLMENT_NO);

    while (1) {
        printf("\n--- File Encryption/Decryption Menu ---\n");
        printf("1. Encrypt File\n");
        printf("2. Decrypt File\n");
        printf("3. Exit\n");
        printf("Select an option: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                printf("\nEnrollment No: %s\n", ENROLLMENT_NO);
                printf("Enter encryption key (integer): ");
                scanf("%d", &key);
                stored_key = key; // Store key for later validation

                fin = fopen(INPUT_FILE, "r");
                if (fin == NULL) {
                    printf("Error: Could not open source file '%s'. Make sure it exists.\n", INPUT_FILE);
                    break;
                }

                fout = fopen(ENCRYPT_FILE, "w");
                if (fout == NULL) {
                    printf("Error: Could not create encrypted file.\n");
                    fclose(fin);
                    break;
                }

                // Encrypt character by character
                while ((ch = fgetc(fin)) != EOF) {
                    fputc(ch + key, fout);
                }

                printf("Success: File encrypted and saved to '%s'.\n", ENCRYPT_FILE);
                fclose(fin);
                fclose(fout);
                break;

            case 2:
                printf("\nEnrollment No: %s\n", ENROLLMENT_NO);
                
                if (stored_key == -1) {
                    printf("Error: No file has been encrypted in this session yet.\n");
                    break;
                }

                printf("Enter decryption key (integer): ");
                scanf("%d", &key);

                // Validation check
                if (key != stored_key) {
                    printf("Error: Incorrect decryption key! Access Denied. Process terminated.\n");
                    break;
                }

                fin = fopen(ENCRYPT_FILE, "r");
                if (fin == NULL) {
                    printf("Error: Could not open encrypted file '%s'.\n", ENCRYPT_FILE);
                    break;
                }

                fout = fopen(DECRYPT_FILE, "w");
                if (fout == NULL) {
                    printf("Error: Could not create decrypted file.\n");
                    fclose(fin);
                    break;
                }

                // Decrypt character by character
                while ((ch = fgetc(fin)) != EOF) {
                    fputc(ch - key, fout);
                }

                printf("Success: File decrypted and saved to '%s'.\n", DECRYPT_FILE);
                stored_key = -1; // Reset key after successful decryption
                fclose(fin);
                fclose(fout);
                break;

            case 3:
                printf("Exiting program...\n");
                exit(0);

            default:
                printf("Invalid choice. Please select 1, 2, or 3.\n");
        }
    }

    return 0;
}