package org.adventcode.day3;

import org.adventcode.common.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

public class Day3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day3.class);

    private static int getPriority(char character) {
        var isLowerCase = Character.isLowerCase(character);
        if (isLowerCase) return character - 96;
        return character - 38;
    }

    private static int getTotalPriorities(List<String> fileContent) {
        int total = 0;
        for (String line : fileContent) {
            int size = line.length();
            HashSet<Character> seen = new HashSet<>();

            for (int i = 0; i < size; i++) {
                char ch = line.charAt(i);
                if (i < size / 2) seen.add(ch);
                else if (seen.contains(ch)) {
                    total += getPriority(ch);
                    seen.remove(ch);
                }
            }
        }
        return total;
    }

    private static int getTeamBadgePriorities(List<String> fileContent) {
        int total = 0;
        HashMap<Character, Integer> counter = new HashMap<>();
        for (int i = 0; i < fileContent.size(); i++) {
            if (i % 3 == 0) counter = new HashMap<>();
            HashSet<Character> seen = new HashSet<>();
            for (char ch : fileContent.get(i).toCharArray()) {
                if (!seen.contains(ch)) counter.put(ch, counter.getOrDefault(ch, 0) + 1);
                if (counter.containsKey(ch) && counter.get(ch) >= 3 && (i + 1) % 3 == 0) {
                    total += getPriority(ch);
                    break;
                }
                seen.add(ch);
            }
        }
        return total;
    }

    public static void main(String[] args) {
        solvePartOne();
        solvePartTwo();
    }

    private static void solvePartTwo() {
        String filename = "input2.txt";
        var response = FileReader.readFile(Day3.class, filename);

        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addToFileContent = fileContent::addAll;
        response.ifPresentOrElse(addToFileContent, () -> FileReader.triggerException(filename));
        LOGGER.info("Total Priorities are {}", getTeamBadgePriorities(fileContent));
    }


    private static void solvePartOne() {
        String filename = "input.txt";
        var response = FileReader.readFile(Day3.class, filename);

        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addToFileContent = fileContent::addAll;
        response.ifPresentOrElse(addToFileContent, () -> FileReader.triggerException(filename));
        LOGGER.info("Total Priorities are {}", getTotalPriorities(fileContent));
    }


}
