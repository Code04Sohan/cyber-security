import java.security.MessageDigest;
import java.util.Scanner;

public class SHA256Example {

    // Random name and enrollment number as requested
    static String Name = "Sarah Jenkins";
    static String Enrollment = "ENR774411225";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- SHA-256 Hashing Algorithm ----");
        System.out.println("Name              : " + Name);
        System.out.println("Enrollment Number : " + Enrollment);
        System.out.println("-----------------------------------\n");

        try {
            // 1. Gather Input
            System.out.print("Enter text to hash: ");
            String input = sc.nextLine();

            // Basic error handling for empty input
            if (input == null || input.trim().isEmpty()) {
                System.err.println("Error: Input text cannot be empty. Cannot generate hash.");
                sc.close();
                return;
            }

            // 2. Initialize MessageDigest for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // 3. Perform the hashing
            byte[] hashBytes = md.digest(input.getBytes());
            
            // 4. Convert the raw bytes to a readable Hexadecimal format
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                // %02x formats the byte to a 2-character lowercase hex string
                hex.append(String.format("%02x", b));
            }

            // 5. Display the results
            System.out.println("\nInput        : " + input);
            System.out.println("SHA-256 Hash : " + hex.toString());

        } catch (Exception e) {
            // Graceful error handling in case the algorithm isn't found
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close(); // Clean up the scanner
        }
    }
}