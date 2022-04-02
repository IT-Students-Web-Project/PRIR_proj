package com.polsl.prir_proj.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FileComparatorTest {

    private FileComparator fileComparator;

    @Test
    void shouldCompareTwoFiles() throws IOException {
        FileContent compared = new FileContent("Test. New kind of test. Next sentence.".getBytes(StandardCharsets.UTF_8));
        FileContent original = new FileContent("Test. New kind of test. Next sentence.".getBytes(StandardCharsets.UTF_8));

        FileComparator fileComparator = new FileComparator();
        ComparisonResult result = fileComparator.compareForSentences(original, compared);

        System.out.println("compared: " + result.getComparedSentences());
        System.out.println("original: " + result.getOriginalSentences());
        System.out.println("identical: " + result.getIdenticalSentences());
        System.out.println("similarity: " + result.getSimilarityDegree() + "%");
        Assertions.assertEquals(result.getComparedSentences(), result.getIdenticalSentences());
    }
}