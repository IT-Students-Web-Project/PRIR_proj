package com.polsl.prir_proj.comparator;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class FileContent {

    List<String> sentences = new ArrayList<>();

    public FileContent(byte[] content)  {
        loadFromFile(content);
    }

    private void loadFromFile(byte[] content)  {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(content)) {
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\.");
            while(scanner.hasNext()) {
                String line = scanner.next();
                sentences.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
