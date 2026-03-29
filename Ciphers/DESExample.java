import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class DESExample {
    
    // Random name and enrollment number as requested
    static String Name = "Emily Chen";
    static String Enrollment = "ENR556677889";

    // 1. Encryption Method
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        
        // FIX: Must use ENCRYPT_MODE here, not DECRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        // FIX: Get bytes of plainText, encrypt them, then encode to Base64 String
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 2. Decryption Method
    public static String decrypt(String cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        // Decode the Base64 String back into encrypted bytes, then decrypt
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes);
    }

    // 3. Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("---- DES Encryption & Decryption ----");
        System.out.println("Name              : " + Name);
        System.out.println("Enrollment Number : " + Enrollment);
        System.out.println("-------------------------------------");

        try {
            // Generate the DES Secret Key automatically
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGen.generateKey();

            // Get Input
            System.out.print("Enter Plain Text: ");
            String plainText = sc.nextLine();

            // Basic Error Handling
            if (plainText == null || plainText.trim().isEmpty()) {
                System.err.println("Error: Plain text cannot be empty.");
                sc.close();
                return;
            }

            // Encrypt
            String encryptedText = encrypt(plainText, secretKey);
            System.out.println("Encrypted Text    : " + encryptedText);

            // Decrypt
            String decryptedText = decrypt(encryptedText, secretKey);
            System.out.println("Decrypted Text    : " + decryptedText);

        } catch (Exception e) {
            System.err.println("Error during DES operation: " + e.getMessage());
        } finally {
            sc.close(); // Prevent memory leaks
        }
    }
}