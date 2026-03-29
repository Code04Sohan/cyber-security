import java.util.Scanner;

public class PlayfairCipherEncryption {

    // Variables exactly as declared in the handwritten notes
    private String Keyword = new String();
    private String Key = new String();
    private char matrix_arr[][] = new char[5][5];

    // 1. Removes duplicate letters from the raw keyword
    public void setKey(String k) {
        String K_adjust = new String();
        boolean flag = false;
        
        // Clean the input first (Uppercase, replace J with I, remove spaces)
        k = k.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        
        if (k.length() > 0) {
            K_adjust = K_adjust + k.charAt(0);
            
            for (int i = 1; i < k.length(); i++) {
                flag = false;
                for (int j = 0; j < K_adjust.length(); j++) {
                    if (k.charAt(i) == K_adjust.charAt(j)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    K_adjust = K_adjust + k.charAt(i);
                }
            }
        }
        Keyword = K_adjust;
    }

    // 2. Appends the rest of the alphabet to the unique keyword
    public void KeyGen() {
        boolean flag = true;
        char current;
        Key = Keyword;

        for (int i = 0; i < 26; i++) {
            current = (char) (i + 65); // ASCII 65 is 'A'
            if (current == 'J') {
                continue;
            }
            
            flag = true;
            for (int j = 0; j < Keyword.length(); j++) {
                if (current == Keyword.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            
            if (flag) {
                Key = Key + current;
            }
        }
    }

    // 3. Fills the 5x5 matrix using the 25-character Key string
    public void generateMatrix() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix_arr[i][j] = Key.charAt(counter);
                counter++;
            }
        }
    }

    // 4. Formats the plaintext (handles double letters and odd length)
    public String formatMessage(String msg) {
        msg = msg.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        String formatted = "";

        for (int i = 0; i < msg.length(); i++) {
            if (i == msg.length() - 1) { // Last character
                formatted = formatted + msg.charAt(i);
            } else if (msg.charAt(i) == msg.charAt(i + 1)) { // Duplicate letters
                formatted = formatted + msg.charAt(i) + "X";
            } else {
                formatted = formatted + msg.charAt(i);
            }
        }

        // If length is odd, append X
        if (formatted.length() % 2 != 0) {
            formatted = formatted + "X";
        }
        return formatted;
    }

    // 5. Gets the row and column of a character in the matrix
    public int[] getCharPos(char ch) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix_arr[i][j] == ch) {
                    pos[0] = i; // Row
                    pos[1] = j; // Column
                    break;
                }
            }
        }
        return pos;
    }

    // 6. Applies the 3 Playfair rules to encrypt
    public String encryptMessage(String msg) {
        String cipherText = "";
        
        for (int i = 0; i < msg.length(); i = i + 2) {
            char char1 = msg.charAt(i);
            char char2 = msg.charAt(i + 1);
            
            int[] pos1 = getCharPos(char1);
            int[] pos2 = getCharPos(char2);

            if (pos1[0] == pos2[0]) {
                // Same Row: shift right
                cipherText = cipherText + matrix_arr[pos1[0]][(pos1[1] + 1) % 5];
                cipherText = cipherText + matrix_arr[pos2[0]][(pos2[1] + 1) % 5];
            } else if (pos1[1] == pos2[1]) {
                // Same Column: shift down
                cipherText = cipherText + matrix_arr[(pos1[0] + 1) % 5][pos1[1]];
                cipherText = cipherText + matrix_arr[(pos2[0] + 1) % 5][pos2[1]];
            } else {
                // Rectangle: swap columns
                cipherText = cipherText + matrix_arr[pos1[0]][pos2[1]];
                cipherText = cipherText + matrix_arr[pos2[0]][pos1[1]];
            }
        }
        return cipherText;
    }

    // 7. Main method to run the program
    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println("Name   : NAME");
        System.out.println("Enroll : 1451561561");
        System.out.println("------------------------------------------------\n");

        Scanner sc = new Scanner(System.in);
        PlayfairCipherEncryption pfc = new PlayfairCipherEncryption();

        System.out.print("Enter the keyword: ");
        String keywordInput = sc.nextLine();

        System.out.print("Enter the plaintext: ");
        String plainText = sc.nextLine();

        // Execute the academic step-by-step process
        pfc.setKey(keywordInput);
        pfc.KeyGen();
        pfc.generateMatrix();
        
        String preparedText = pfc.formatMessage(plainText);
        String encryptedText = pfc.encryptMessage(preparedText);

        System.out.println("\n--- Step-by-Step Results ---");
        System.out.println("1. Processed Keyword : " + pfc.Keyword);
        System.out.println("2. Full 25-char Key  : " + pfc.Key);
        System.out.println("3. Formatted Message : " + preparedText);
        System.out.println("4. Final Ciphertext  : " + encryptedText);

        sc.close();
    }
}