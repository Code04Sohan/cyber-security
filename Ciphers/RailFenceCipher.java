import java.util.Arrays;
import java.util.Scanner;

public class RailFenceCipher {

    // Random name and enrollment number as requested
    static String Name = "Michael Chang";
    static String Enrollment = "ENR203948576";

    // 1. Encryption Method
    public static String encrypt(String text, int key) {
        // If key is 1, no encryption happens
        if (key <= 1) return text;

        char[][] rail = new char[key][text.length()];
        
        // Fill the 2D array with a placeholder ('\n')
        for (int i = 0; i < key; i++) {
            Arrays.fill(rail[i], '\n');
        }

        boolean dirDown = false;
        int row = 0, col = 0;

        // Traverse and place characters in a zig-zag pattern
        for (int i = 0; i < text.length(); i++) {
            // Change direction at the top or bottom rail
            if (row == 0) {
                dirDown = true;
            } else if (row == key - 1) {
                dirDown = false;
            }

            rail[row][col++] = text.charAt(i);
            
            // Move up or down
            row += dirDown ? 1 : -1;
        }

        // Construct the cipher text by reading row by row
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (rail[i][j] != '\n') {
                    result.append(rail[i][j]);
                }
            }
        }
        return result.toString();
    }

    // 2. Decryption Method
    public static String decrypt(String cipher, int key) {
        if (key <= 1) return cipher;

        char[][] rail = new char[key][cipher.length()];
        
        // Fill the 2D array with a placeholder ('\n')
        for (int i = 0; i < key; i++) {
            Arrays.fill(rail[i], '\n');
        }

        // Step 1: Mark the zig-zag path with '*'
        boolean dirDown = false;
        int row = 0, col = 0;
        
        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0) {
                dirDown = true;
            } else if (row == key - 1) {
                dirDown = false;
            }
            
            rail[row][col++] = '*';
            row += dirDown ? 1 : -1;
        }

        // Step 2: Fill the marked '*' positions with the ciphertext characters row by row
        int index = 0;
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                if (rail[i][j] == '*' && index < cipher.length()) {
                    rail[i][j] = cipher.charAt(index++);
                }
            }
        }

        // Step 3: Read the matrix in a zig-zag manner to reconstruct the original text
        StringBuilder result = new StringBuilder();
        row = 0; 
        col = 0;
        
        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0) {
                dirDown = true;
            } else if (row == key - 1) {
                dirDown = false;
            }

            if (rail[row][col] != '\n') {
                result.append(rail[row][col++]);
            }
            row += dirDown ? 1 : -1;
        }
        
        return result.toString();
    }

    // 3. Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Rail Fence Cipher ----");
        System.out.println("Name              : " + Name);
        System.out.println("Enrollment Number : " + Enrollment);
        System.out.println("---------------------------\n");

        // Input gathering with error handling
        System.out.print("Enter Plain Text: ");
        String text = sc.nextLine();

        if (text == null || text.trim().isEmpty()) {
            System.err.println("Error: Text cannot be empty.");
            sc.close();
            return;
        }

        System.out.print("Enter Key (number of rails): ");
        int key;
        
        // Ensure the key is a valid integer
        if (sc.hasNextInt()) {
            key = sc.nextInt();
            if (key <= 0) {
                System.err.println("Error: Key must be a positive integer greater than 0.");
                sc.close();
                return;
            }
        } else {
            System.err.println("Error: Invalid key format. Please enter an integer.");
            sc.close();
            return;
        }

        // Remove spaces for traditional cipher formatting (optional, but standard)
        text = text.replaceAll("\\s+", "");

        // Execute and print results
        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("\nOriginal Text  : " + text);
        System.out.println("Encrypted Text : " + encrypted);
        System.out.println("Decrypted Text : " + decrypted);

        sc.close();
    }
}