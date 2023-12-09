package org.adventcode.day1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static int getMeTheDigits(String line) {
        Integer firstIndex = null;
        Integer lastIndex = null;

        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if (Character.isDigit(currChar)) {
                if (firstIndex == null) firstIndex = i;
                else lastIndex = i;
            }
        }
        if (firstIndex == null) return 0;
        if (lastIndex == null) lastIndex = firstIndex;

        return Integer.parseInt(line.charAt(firstIndex) + "" + line.charAt(lastIndex));
    }

    public static void main(String[] args) {


        try (InputStream inputStream = App.class.getResourceAsStream("input.txt")) {

            assert inputStream != null;
            InputStreamReader reader = new InputStreamReader(inputStream);

            int total = 0;
            String line;

            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                total += getMeTheDigits(line);
            }

            String fmt = String.format("The total calibration values are .... %d", total);
            logger.info(fmt);

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error("Error Reading file: {}", e.getMessage());
        }
    }
}

