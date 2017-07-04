package com.example.concurrency;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ReadFile {
    /**
     * Reads new line separated ticker from file in resources directory
     *
     * @param fileName File name as in resources
     * @return list of ticker as string
     */
    static List<String> readTicker(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        if (file.exists()) {
            String content = null;
            try {
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert content != null;
            return Arrays.stream(content.split("\n")).collect(Collectors.toList());
        } else {
            throw new RuntimeException("File not found");
        }
    }
}
