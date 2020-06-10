package com.homurax.kmeans.common.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VocabularyLoader {

    public static Map<String, Integer> load(Path path) throws IOException {
        int index = 0;
        HashMap<String, Integer> vocIndex = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                vocIndex.put(line, index);
                index++;
            }
        }
        return vocIndex;
    }

}