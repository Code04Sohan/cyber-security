import java.util.Scanner;

public class HillCipher {
    
    // Random name and enrollment number as requested
    static String Name = "John Doe";
    static String ENROLLMENT = "ENR987654321";
    
    // Key Matrix from your notes
    static int[][] keyMatrix = {
        {3, 3},
        {2, 5}
    };

    // 1. Encryption Method
    static String encrypt(String plaintext) {
        // Clean input
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        
        if (plaintext.isEmpty()) {
            return ""; // Return empty if no valid characters
        }

        // Pad with 'X' if the length is odd
        if (plaintext.length() % 2 != 0) {
            plaintext += "X";
        }

        StringBuilder CipherText = new StringBuilder();
        
        for (int i = 0; i < plaintext.length(); i += 2) {
            int P1 = plaintext.charAt(i) - 'A';
            int P2 = plaintext.charAt(i + 1) - 'A';

            // Matrix multiplication mod 26
            int C1 = (keyMatrix[0][0] * P1 + keyMatrix[0][1] * P2) % 26;
            int C2 = (keyMatrix[1][0] * P1 + keyMatrix[1][1] * P2) % 26;

            CipherText.append((char) (C1 + 'A'));
            CipherText.append((char) (C2 + 'A'));
        }
        
        return CipherText.toString();
    }

    // 2. Decryption Method (Added to complete the cipher)
    static String decrypt(String ciphertext) {
        if (ciphertext.isEmpty()) return "";

        // Step A: Calculate determinant: (ad - bc) mod 26
        int det = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26;
        if (det < 0) {
            det += 26; // Fix negative modulo
        }

        // Step B: Find modular inverse of determinant (detInv)
        int detInv = -1;
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }

        // Error handling if matrix is not invertible mod 26
        if (detInv == -1) {
            System.err.println("Error: The provided key matrix is not invertible modulo 26. Decryption impossible.");
            System.exit(1);
        }

        // Step C: Calculate the inverse matrix mod 26
        int[][] invMatrix = new int[2][2];
        invMatrix[0][0] = ( keyMatrix[1][1] * detInv) % 26;
        invMatrix[0][1] = (-keyMatrix[0][1] * detInv) % 26;
        invMatrix[1][0] = (-keyMatrix[1][0] * detInv) % 26;
        invMatrix[1][1] = ( keyMatrix[0][0] * detInv) % 26;

        // Ensure all elements in inverse matrix are positive mod 26
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (invMatrix[i][j] < 0) {
                    invMatrix[i][j] += 26;
                }
            }
        }

        // Step D: Decrypt using the inverse matrix
        StringBuilder PlainText = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int C1 = ciphertext.charAt(i) - 'A';
            int C2 = ciphertext.charAt(i + 1) - 'A';

            int P1 = (invMatrix[0][0] * C1 + invMatrix[0][1] * C2) % 26;
            int P2 = (invMatrix[1][0] * C1 + invMatrix[1][1] * C2) % 26;

            PlainText.append((char) (P1 + 'A'));
            PlainText.append((char) (P2 + 'A'));
        }
        
        return PlainText.toString();
    }

    // 3. Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--- Hill Cipher Encryption & Decryption ---");
        System.out.println("Name            : " + Name);
        System.out.println("Enrollment Number: " + ENROLLMENT);
        System.out.println("-------------------------------------------");
        
        System.out.print("Enter plain text: ");
        String plaintext = sc.nextLine();
        
        // Input validation
        if (plaintext.trim().isEmpty()) {
            System.err.println("Error: Plain text cannot be empty.");
            sc.close();
            return;
        }

        // Perform Encryption
        String CipherText = encrypt(plaintext);
        System.out.println("Encrypted Text  : " + CipherText);
        
        // Perform Decryption
        String DecryptedText = decrypt(CipherText);
        System.out.println("Decrypted Text  : " + DecryptedText);
        
        sc.close();
    }
}