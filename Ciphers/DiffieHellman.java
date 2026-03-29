import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class DiffieHellman {

    // Random name and enrollment number as requested
    static String Name = "Alex Carter";
    static String Enrollment = "ENR992288337";

    // A simple encryption method using the shared secret key
    public static String encryptMessage(String plainText, int shiftKey) {
        StringBuilder cipherText = new StringBuilder();
        // Shift each character by the key amount (Basic Caesar Cipher approach)
        for (int i = 0; i < plainText.length(); i++) {
            cipherText.append((char) (plainText.charAt(i) + shiftKey));
        }
        return cipherText.toString();
    }

    // A simple decryption method to reverse the process
    public static String decryptMessage(String cipherText, int shiftKey) {
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            plainText.append((char) (cipherText.charAt(i) - shiftKey));
        }
        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Diffie-Hellman Algorithm ----");
        System.out.println("Name              : " + Name);
        System.out.println("Enrollment Number : " + Enrollment);
        System.out.println("----------------------------------\n");

        try {
            // 1. Get Public Prime and Primitive Root
            System.out.print("Enter prime number (P): ");
            BigInteger p = sc.nextBigInteger();

            System.out.print("Enter primitive root (g): ");
            BigInteger g = sc.nextBigInteger();

            // Clear the newline character left over from nextBigInteger
            sc.nextLine(); 

            // 2. Get the Plaintext Message
            System.out.print("Enter plain text message to encrypt: ");
            String plainText = sc.nextLine();

            if (plainText == null || plainText.trim().isEmpty()) {
                System.err.println("Error: Plain text cannot be empty.");
                return;
            }

            // 3. Generate Random Private Keys for Alice (a) and Bob (b)
            Random rand = new Random();
            BigInteger a = new BigInteger(5, rand); // 5-bit random integer
            BigInteger b = new BigInteger(5, rand);

            System.out.println("\n--- Generating Keys ---");
            System.out.println("Alice Private Key (a): " + a);
            System.out.println("Bob Private Key (b)  : " + b);

            // 4. Calculate Public Keys (A = g^a mod p) and (B = g^b mod p)
            BigInteger A = g.modPow(a, p);
            BigInteger B = g.modPow(b, p);

            System.out.println("Alice Public Key (A) : " + A);
            System.out.println("Bob Public Key (B)   : " + B);

            // 5. Generate the Shared Secret Keys
            // Alice computes: B^a mod p
            // Bob computes: A^b mod p
            BigInteger key1 = B.modPow(a, p);
            BigInteger key2 = A.modPow(b, p);

            System.out.println("\n--- Key Exchange Result ---");
            System.out.println("Shared Secret Key (Alice): " + key1);
            System.out.println("Shared Secret Key (Bob)  : " + key2);

            // Verify the exchange was successful
            if (!key1.equals(key2)) {
                System.err.println("Error: Shared keys do not match! Key exchange failed.");
                return;
            }

            // 6. Encrypt and Decrypt using the established shared secret
            // Convert the BigInteger key to a standard integer for our simple shift cipher
            int sharedKeyInt = key1.intValue();

            String encryptedText = encryptMessage(plainText, sharedKeyInt);
            String decryptedText = decryptMessage(encryptedText, sharedKeyInt);

            System.out.println("\n--- Message Encryption ---");
            System.out.println("Original Text  : " + plainText);
            System.out.println("Encrypted Text : " + encryptedText);
            System.out.println("Decrypted Text : " + decryptedText);

        } catch (InputMismatchException e) {
            System.err.println("\nError: Invalid input. P and g must be integers.");
        } catch (Exception e) {
            System.err.println("\nAn unexpected error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}