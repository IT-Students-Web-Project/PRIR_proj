package com.polsl.prir_proj.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

class FileComparatorTest {

    private FileComparator fileComparator;

    @Test
    void shouldCompareTwoFiles() throws FileNotFoundException {
        FileContent compared = new FileContent(new File("data/file1.txt"));
        FileContent original = new FileContent(new File("data/temp/compared.txt"));

        FileComparator fileComparator = new FileComparator();
        ComparisonResult result = fileComparator.compareForSentences(original, compared);

        System.out.println("compared: " + result.getComparedSentences());
        System.out.println("original: " + result.getOriginalSentences());
        System.out.println("identical: " + result.getIdenticalSentences());
        System.out.println("similarity: " + result.getSimilarityDegree() + "%");
        Assertions.assertEquals(result.getComparedSentences(), result.getIdenticalSentences());
    }
}