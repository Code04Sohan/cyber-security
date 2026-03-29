import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitlength = 1024;
    private Random r;

    // Default Constructor: Generates keys
    public RSA() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        
        N = p.multiply(q);
        
        // phi = (p-1)*(q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        // Find e such that gcd(e, phi) = 1 and 1 < e < phi
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }
        
        // Calculate d as the modular inverse of e
        d = e.modInverse(phi);
    }

    // Parameterized Constructor
    public RSA(BigInteger e, BigInteger d, BigInteger N) {
        this.e = e;
        this.d = d;
        this.N = N;
    }

    // Encrypt method
    public BigInteger encrypt(BigInteger plaintext) {
        return plaintext.modPow(e, N);
    }

    // Decrypt method
    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, N);
    }

    // Helper method to display bytes properly
    public static String bytesToString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Byte.toString(b)).append(" ");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Random Credentials as requested
        System.out.println("------------------------------------------------");
        System.out.println("Name   : Alice Smith");
        System.out.println("Enroll : ENR847392015");
        System.out.println("------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        RSA rsa = new RSA();
        String teststring;

        System.out.print("Enter the plaintext: ");
        teststring = scanner.nextLine();

        // Basic error handling for empty input
        if (teststring == null || teststring.trim().isEmpty()) {
            System.err.println("Error: Plaintext cannot be empty. Terminating.");
            scanner.close();
            return;
        }

        System.out.println("\nEncrypting String : " + teststring);
        System.out.println("String in Bytes   : " + bytesToString(teststring.getBytes()));

        // Convert string to BigInteger (1 ensures it's treated as a positive number)
        BigInteger plaintext = new BigInteger(1, teststring.getBytes());
        
        // Encrypt
        BigInteger ciphertext = rsa.encrypt(plaintext);
        System.out.println("Ciphertext        : " + ciphertext);

        // Decrypt
        BigInteger decryptedText = rsa.decrypt(ciphertext);
        byte[] decryptedBytes = decryptedText.toByteArray();
        
        // Handle potential leading zero byte from BigInteger conversion
        if(decryptedBytes[0] == 0) {
            byte[] tmp = new byte[decryptedBytes.length - 1];
            System.arraycopy(decryptedBytes, 1, tmp, 0, tmp.length);
            decryptedBytes = tmp;
        }

        System.out.println("Decrypted String  : " + new String(decryptedBytes));
        
        scanner.close();
    }
}