package com.polsl.prir_proj.comparator;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultithreadFileComparatorTest {



    @Test
    void shouldCompareMultipleFilesToOriginal() throws InterruptedException, FileNotFoundException {
        int threads = Runtime.getRuntime().availableProcessors();

        List<File> files = new ArrayList<>();
        for(int i = 1; i <5; i++) {
            files.add(new File("data/file" + i + ".txt"));
        }

        File compared = new File("data/temp/compared.txt");

        MultithreadFileComparator fileComparator = new MultithreadFileComparator(threads, compared, files);
        List<ComparisonResult> results = fileComparator.execute();

        for(ComparisonResult result : results) {
            System.out.println("Stopień podobieństwa: " + result.getSimilarityDegree());
        }
    }
}