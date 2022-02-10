/**
 * The birthday paradox is the high probability that
 * 2 people in a group have a 99.9% probability of
 * sharing the same birthday.
 * 
 * This program performs monte carlo experience
 * to calculate the probability that 2 people share
 * the same birthday in a group of size n.
 * https://inventwithpython.com/bigbookpython/project2.html#
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class BirthdayParadox {
    Integer simulationSize;
    Integer groupSize;
    public static final Logger LOGGER = Logger.getLogger(BirthdayParadox.class.getName());

    public BirthdayParadox(Integer simulationSizeInput, Integer groupSizeInput) {
        /** Method constructor
         * @params  simulationSizeInput     number of simulation to run
         * @params  groupSizeInput          size of the group
         */
        this.simulationSize = simulationSizeInput;
        this.groupSize = groupSizeInput;
    }

    public static List<String> getRandomBirthday(int groupSize, int minYear, int maxYear) {
        /** Given a group size, this method will return `n` random birthday
         * between 1922-2022 where `n=groupSize`.
         * 
         * @param   groupSize   the number of random birthday to return
         * @param   minYear     the min year [lower bound]
         * @param   maxYear     the max year [upper bound]
         * @return              a list of random birthday with format YYYY-MM-DD
         */
        
        ArrayList<String> birthdays = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd");

        for (int i = 0; i < groupSize; i++) {
            LocalDate baseDate = LocalDate.now();
            LocalDate baseYear = baseDate.withYear(ThreadLocalRandom.current().nextInt(minYear, maxYear));

            int dayOfYear = ThreadLocalRandom.current().nextInt(1, baseYear.lengthOfYear());

            LocalDate baseRandBirthday = baseYear.withDayOfYear(dayOfYear);

            LocalDate randDate = LocalDate.of(
                baseRandBirthday.getYear(),
                baseRandBirthday.getMonth(),
                baseRandBirthday.getDayOfMonth()
            );


            String formattedDate = dateFormat.format(randDate);
            birthdays.add(formattedDate);
        }

        Collections.sort(birthdays);

        return birthdays;
    }

    public static Boolean checkSameBirthdate(List<String>  birthdates) {
        /** Given a list of birth date check the number of people sharing the
         * same birth date
         * @param   birthdates  A list of birthdate
         * @return              number of similar birthdate
         */

        for (int i = 0; i < birthdates.size() - 1; i++) {
            String bDateToCompare = birthdates.get(i);
            if (bDateToCompare.equals(birthdates.get(i+1))) {
                return true;
            } 
        }

        return false;
    }

    public static void main(String[] args) {
        // main method
        
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the number of simulation to run:  ");
        Integer simulationSizeInput = reader.nextInt();

        System.out.print("Enter the number of birthday to generate:  ");
        Integer groupSizeInput = reader.nextInt();

        BirthdayParadox bParadox = new BirthdayParadox(simulationSizeInput, groupSizeInput);
        Integer sameBDate = 0;

        for (int i = 0; i < bParadox.simulationSize; i++) {
            if (i % 10000 == 0) {
                LOGGER.info(String.format("processed %s simultions", i));
            }
            List<String> bDay = getRandomBirthday(bParadox.groupSize, 1960, 2022);
            if (Boolean.TRUE.equals(checkSameBirthdate(bDay))) {
                sameBDate++;
            }
        }

        System.out.println(String.format("Out of %s, %s had a matching birthdate", bParadox.simulationSize, sameBDate));
        System.out.println(String.format("This gives a probability of %s", (float)sameBDate/bParadox.simulationSize));

    }
}
