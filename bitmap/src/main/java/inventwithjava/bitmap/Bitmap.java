package bitmap.src.main.java.inventwithjava.bitmap;
import java.io.*;
import java.util.Scanner;
import java.util.logging.*;
/**
 * Uses a multiline string as a bitmap to determine how to represent a 
 * message from a user. The provided bitmap represents a world map.
 * Adapted from Invent with Python https://inventwithpython.com/bigbookpython/project3.html
 */
public class Bitmap 
{

    public static final Logger LOGGER = Logger.getLogger(Bitmap.class.getName());

    public static BufferedReader readFile(String path) throws IOException {
        FileReader file = new FileReader(path);

        return new BufferedReader(file);
    }
        
    public static void main(String[] args ) {
        try {
            BufferedReader br = readFile("bitmap/src/main/java/inventwithjava/bitmap/bitmap.txt");

            Scanner scanner = new Scanner(System.in);
            LOGGER.info("enter a message");
            String message = scanner.nextLine();
        
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        System.out.print(' ');
                    } else {
                        System.out.print(message.charAt(i % message.length()));
                    }
                }
                System.out.print("\n");
            } 
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "error", e);
        }
    }
}
