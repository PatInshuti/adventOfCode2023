package org.adventcode.day1;

import org.adventcode.common.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Day1 {
    private static final Logger logger = LoggerFactory.getLogger(Day1.class);

    private static final Map<String, Character> digitMapping = Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    private static final BiFunction<String, Integer, Integer> computeLine = (String line, Integer option) -> {
        switch (option) {
            case 1 -> {
                return getMeTheDigits(line);
            }
            case 2 -> {
                return getMeTheDigitsPartTwo(line);
            }
            default -> {
                return -1;
            }
        }
    };


    private static int getMeTheDigits(String line) {
        Integer firstIndex = null;
        Integer lastIndex = null;

        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if (Character.isDigit(currChar)) {
                if (firstIndex == null) firstIndex = i;
                lastIndex = i;
            }
        }
        if (firstIndex == null) return 0;
        return Integer.parseInt(line.charAt(firstIndex) + "" + line.charAt(lastIndex));
    }

    private static int getMeTheDigitsPartTwo(String line) {

        Character firstDigit = null;
        Character lastDigit = null;

        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if (Character.isDigit(currChar)) {
                if (firstDigit == null) firstDigit = currChar;
                lastDigit = currChar;
            }

            for (int j = i; j < Math.min(i + 5, line.length()); j++) {
                String currSubString = line.substring(i, j + 1);
                if (digitMapping.containsKey(currSubString)) {
                    if (firstDigit == null) firstDigit = digitMapping.get(currSubString);
                    lastDigit = digitMapping.get(currSubString);
                }
            }
        }

        return firstDigit == null ? 0 : Integer.parseInt(firstDigit + "" + lastDigit);
    }


    public static void main(String[] args) {

        String filename = "input.txt";
        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addToList = fileContent::addAll;

        var response = FileReader.readFile(Day1.class, filename);
        response.ifPresentOrElse(addToList, () -> FileReader.triggerException(filename));

        int total = 0;

        for (String line : fileContent) {
            total += computeLine.apply(line, 2);
        }

        String fmt = String.format("The total calibration values are .... %d", total);
        logger.info(fmt);
    }
}

