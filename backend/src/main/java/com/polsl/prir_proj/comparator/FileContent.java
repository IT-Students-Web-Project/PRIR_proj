package com.polsl.prir_proj.comparator;

import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class FileContent {

    List<String> sentences = new ArrayList<>();

    public FileContent(File file) throws FileNotFoundException {
        loadFromFile(file);
    }

    private void loadFromFile(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter("\\.");
            while(scanner.hasNext()) {
                String line = scanner.next();
                sentences.add(line);
            }
        }
    }
}
