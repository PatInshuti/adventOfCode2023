package org.adventcode.day2;

import org.adventcode.common.FileReaderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Day2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day2.class);

    public enum Limit {
        RED(12),
        GREEN(13),
        BLUE(14);
        public final int val;

        Limit(int val) {
            this.val = val;
        }
    }

    public static boolean validatePick(String pick) {
        var listOfColors = pick.split(",");
        for (String color : listOfColors) {
            var cleanedColor = color.strip();

            StringBuilder count = new StringBuilder();
            for (Character ch : cleanedColor.toCharArray()) {
                if (Character.isDigit(ch)) count.append(ch);
                else break;
            }

            if ((cleanedColor.contains("blue") && Integer.parseInt(count.toString()) > Limit.BLUE.val) ||
                    (cleanedColor.contains("green") && Integer.parseInt(count.toString()) > Limit.GREEN.val) ||
                    (cleanedColor.contains("red") && Integer.parseInt(count.toString()) > Limit.RED.val))
                return false;
        }
        return true;
    }

    private static boolean isValidGame(String gameRounds) {

        var listOfGameRounds = gameRounds.split(";");
        for (String gameRound : listOfGameRounds) {
            if (!validatePick(gameRound.strip())) return false;
        }

        return true;
    }

    public static void main(String[] args) throws RuntimeException {

        String filename = "input.txt";
        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addToList = fileContent::addAll;

        var response = FileReaderHelper.readFile(Day2.class, filename);
        response.ifPresentOrElse(addToList, () -> FileReaderHelper.triggerException(filename));

        int totalIds = 0;
        for (String line : fileContent) {
            var list = line.split(":");
            int gameId = Integer.parseInt(list[0].substring(5));

            String gameRounds = list[1].strip();
            if (isValidGame(gameRounds)) totalIds += gameId;
        }
        String fmt = String.format("The total of valid IDs values are .... %d", totalIds);
        LOGGER.info(fmt);
    }
}