package org.adventcode.common;

import org.adventcode.day2.Day2;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

/*
 * Class to generically read a file into memory from a particular class or File Path
 * author: patrick
 * */

public class FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    private FileReader() {
    }

    public static void triggerException(String filename) {
        throw new MissingResourceException("Error retrieving content", Day2.class.getName(), filename);
    }

    public static Optional<List<String>> readFile(Class<?> clazz, String filename) {
        List<String> fileContent;
        try (InputStream inputStream = clazz.getResourceAsStream(filename)) {
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            fileContent = bufferedReader.lines().toList();
            return Optional.of(fileContent);
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Error reading file: {}", filename);
            return Optional.empty();
        }

    }
}
