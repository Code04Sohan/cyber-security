import java.security.MessageDigest;

/**
 * Name: Alex Mercer
 * Enrollment No: CS-2026-84739
 * Assignment: MD5 Hashing Algorithm Implementation
 */
public class MD5Example {
    
    public static void main(String[] args) {
        try {
            // 1. Define the input string
            String input = "cybersecurity";

            // 2. Get an instance of the MD5 MessageDigest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 3. Compute the hash (returns an array of bytes)
            byte[] hashBytes = md.digest(input.getBytes());

            // 4. Convert the byte array into a readable Hexadecimal format
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                // %02x formats the byte to a 2-character lowercase hexadecimal string
                hex.append(String.format("%02x", b));
            }

            // 5. Print the Input and the corresponding Output
            System.out.println("Name         : Alex Mercer");
            System.out.println("Enrollment No: CS-2026-84739");
            System.out.println("--------------------------------------------------");
            System.out.println("Input        : " + input);
            System.out.println("MD5 Hash     : " + hex.toString());

        } catch (Exception e) {
            // 6. Error handling
            System.err.println("An error occurred during the hashing process:");
            e.printStackTrace();
        }
    }
}