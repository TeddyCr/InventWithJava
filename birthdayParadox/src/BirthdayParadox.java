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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.GregorianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

public class BirthdayParadox {


    public BirthdayParadox() {
        // class constructor
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

        return birthdays;
    }

    public static void main(String[] args) {
        // main method
        List<String> bDay = getRandomBirthday(40, 1960, 2022);
        System.out.println(bDay);
    }
}
