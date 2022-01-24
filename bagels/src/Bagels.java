package bagels.src;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;


public class Bagels {
    String computerGuess;
    public static final int ALLOWEDATTEMPTS = 10;
    public static final Logger LOGGER = Logger.getLogger(Bagels.class.getName());


    public Bagels(String computerInput) {
        this.computerGuess = computerInput;
    }

    public static String generateRandomNumber() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt(900) + 100);
    }

    public static String compareStrings(String userGuess, String computerGuess) {
        if (userGuess.equals(computerGuess)) {
            return "Congratulations!";
        }

        String[] numsUserGuess = userGuess.split("");
        String[] numsComputerGuess = computerGuess.split("");

        String[] clues = new String[3];

        for (int i = 0; i < numsUserGuess.length; i++) {
            String numUserGuess = numsUserGuess[i];
            if (numUserGuess.equals(numsComputerGuess[i])) {
                LOGGER.info("1 number is in the right position");
                clues[i] = "Fermi";
            } else if (Arrays.stream(numsComputerGuess).anyMatch(el -> el.contains(numUserGuess))) {
                LOGGER.info("1 number is correct but in the wrong position");
                clues[i] = "Pico";
            }
        }

        for (String clue: clues) {
            if (clue != null) {
                return String.join(" ", clues);
            }
        }

        return "Bagels";
    }

    public static void checkInput(String userInput) throws InputValidationException {
        try {
            int x = Integer.parseInt(userInput);
            if (x < 100) {
                throw new InputValidationException("Input number was less than 100. Make sure to enter a number between 100 and 999");
            } else if (x > 999) {
                throw new InputValidationException("Input number was greater than 999. Make sure to enter a number between 100 and 999");
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("You did not input a number, Make sure to enter a number between 100 and 999");
        }
    }

    public static void main(String[] args) {
        Bagels gme = new Bagels(generateRandomNumber());
        Scanner reader = new Scanner(System.in);

        int attempts = 0;
        while (attempts < ALLOWEDATTEMPTS) {
            LOGGER.info("Enter a number between 100-999: ");
            String userInput = reader.nextLine();

            try {
                checkInput(userInput);
            } catch (InputValidationException e) {
                LOGGER.warning(e.getMessage());
                continue;
            }
            
            String res = compareStrings(userInput, gme.computerGuess);
            LOGGER.info(res);
            if (userInput.equals(gme.computerGuess)) {
                String msg = String.format("Congratulations, you guessed the number %s", gme.computerGuess);
                LOGGER.info(msg);
                break;
            }
            attempts += 1;
            String attmptMsg = String.format("You have %s attempts left%n---%n", ALLOWEDATTEMPTS - attempts);
            LOGGER.info(attmptMsg);    
        }

        if (attempts == ALLOWEDATTEMPTS) {
            String msg = String.format("You did not find the number %s", gme.computerGuess);
            LOGGER.info(msg);
        }
        reader.close();
    }
}