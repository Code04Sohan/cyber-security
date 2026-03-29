import java.util.Scanner;

public class VigenereCipher {

    // Random name and enrollment number as requested
    static String Name = "David Kim";
    static String Enrollment = "ENR304958671";

    // 1. Generate Key Method
    static String generateKey(String text, String key) {
        StringBuilder newKey = new StringBuilder(key);
        for (int i = 0; newKey.length() < text.length(); i++) {
            // Append characters from the original key repeatedly
            newKey.append(key.charAt(i % key.length()));
        }
        return newKey.toString();
    }

    // 2. Encryption Method
    static String encrypt(String text, String key) {
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            // The clever ASCII math from your notes
            int x = (text.charAt(i) + key.charAt(i)) % 26;
            x += 'A';
            cipherText.append((char) x);
        }
        return cipherText.toString();
    }

    // 3. Decryption Method
    static String decrypt(String cipherText, String key) {
        StringBuilder originalText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            // Reversing the math (+ 26 ensures no negative numbers before modulo)
            int x = (cipherText.charAt(i) - key.charAt(i) + 26) % 26;
            x += 'A';
            originalText.append((char) x);
        }
        return originalText.toString();
    }

    // 4. Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Vigenere Cipher ----");
        System.out.println("Name              : " + Name);
        System.out.println("Enrollment Number : " + Enrollment);
        System.out.println("-------------------------\n");

        // Input gathering with error handling
        System.out.print("Enter Plain Text: ");
        String textInput = sc.nextLine();

        System.out.print("Enter Keyword   : ");
        String keywordInput = sc.nextLine();

        if (textInput == null || textInput.trim().isEmpty() || keywordInput == null || keywordInput.trim().isEmpty()) {
            System.err.println("Error: Text and Keyword cannot be empty.");
            sc.close();
            return;
        }

        // Clean inputs: Convert to uppercase and remove non-alphabetic characters
        // This is strictly required for the (char + char) % 26 math trick to work!
        String text = textInput.toUpperCase().replaceAll("[^A-Z]", "");
        String keyword = keywordInput.toUpperCase().replaceAll("[^A-Z]", "");

        if (text.isEmpty() || keyword.isEmpty()) {
            System.err.println("Error: Inputs must contain alphabetic characters.");
            sc.close();
            return;
        }

        // Execute the cipher steps
        String key = generateKey(text, keyword);
        String cipherText = encrypt(text, key);
        String decryptedText = decrypt(cipherText, key);

        // Display Results
        System.out.println("\nProcessed Text : " + text);
        System.out.println("Generated Key  : " + key);
        System.out.println("Encrypted Text : " + cipherText);
        System.out.println("Decrypted Text : " + decryptedText);

        sc.close();
    }
}