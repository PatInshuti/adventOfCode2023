package org.adventcode.day2;

import org.adventcode.common.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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

    private static void solvePartOne(String filename) {
        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addToList = fileContent::addAll;

        var response = FileReader.readFile(Day2.class, filename);
        response.ifPresentOrElse(addToList, () -> FileReader.triggerException(filename));

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

    private static void solvePartTwo(String filename) {
        List<String> fileContent = new ArrayList<>();
        Consumer<List<String>> addContent = fileContent::addAll;

        Optional<List<String>> response = FileReader.readFile(Day2.class, filename);
        response.ifPresentOrElse(addContent, () -> FileReader.triggerException(filename));

        HashMap<String, int[]> scores = constructScores(fileContent);
        int totalScore = extractScoreSum(scores);

        String fmt = String.format("Min Values for valid game .... %d", totalScore);
        LOGGER.info(fmt);
    }

    private static HashMap<String, int[]> constructScores(List<String> fileContent) {
        HashMap<String, int[]> scores = new HashMap<>();
        for (String line : fileContent) {
            var gameInfo = line.split(":");
            var gameId = gameInfo[0];
            scores.put(gameId, new int[3]);

            var gameRounds = gameInfo[1].split(";");

            for (String gameRound : gameRounds) {
                var roundDraws = gameRound.split(",");
                var newScore = setScorePerRound(roundDraws, scores, gameId);
                scores.put(gameId, newScore);
            }
        }
        return scores;
    }

    private static int[] setScorePerRound(String[] roundDraws, HashMap<String, int[]> scores, String gameId) {
        var colorScore = scores.get(gameId);
        for (String roundDraw : roundDraws) {
            String cleanRound = roundDraw.strip();
            var currScore = Integer.parseInt(cleanRound.split(" ")[0]);

            if (cleanRound.contains("red") && (currScore > colorScore[0]))
                colorScore[0] = currScore;

            else if (cleanRound.contains("green") && (currScore > colorScore[1]))
                colorScore[1] = currScore;

            else if (cleanRound.contains("blue") && (currScore > colorScore[2]))
                colorScore[2] = currScore;
        }
        return colorScore;
    }

    private static int extractScoreSum(HashMap<String, int[]> scores) {
        int totalScore = 0;
        for (int[] arr : scores.values()) {
            int currScore = 1;
            for (int num : arr) currScore *= num;
            totalScore += currScore;
        }
        return totalScore;
    }

    public static void main(String[] args) {
        solvePartOne("input.txt");
        solvePartTwo("input2.txt");
    }
}