package com.homurax.searchWithoutIndexing.serial.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SerialStringAccumulator implements BiConsumer<ArrayList<String>, Path> {

    private final String word;

    public SerialStringAccumulator(String word) {
        this.word = word;
    }

    @Override
    public void accept(ArrayList<String> list, Path path) {
        try {
            long counter = Files
                    .lines(path)
                    .map(l -> l.split(":")[1].toLowerCase())
                    .filter(l -> l.contains(word.toLowerCase()))
                    .count();
            if (counter > 0) {
                list.add(path.toString());
            }
        } catch (Exception e) {
            System.out.println(path);
            e.printStackTrace();
        }
    }

}
