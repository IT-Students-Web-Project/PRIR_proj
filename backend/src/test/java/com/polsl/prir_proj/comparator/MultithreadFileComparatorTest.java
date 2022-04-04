package com.polsl.prir_proj.comparator;

import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.repositories.FileRepository;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultithreadFileComparatorTest {

    @Test
    void shouldCompareMultipleFilesToOriginal() throws InterruptedException, FileNotFoundException {
        int threads = Runtime.getRuntime().availableProcessors();

        HashMap<String, byte[]> contents = new HashMap();
        for(int i = 1; i <5; i++) {
            contents.put("T" + i,"Test. New kind of test. Next sentence.".getBytes(StandardCharsets.UTF_8));
        }
        contents.put("test","Test. New kind of test. Next sentence. 100.".getBytes(StandardCharsets.UTF_8));
        FileContent compared = new FileContent("Test. New kind of test. Next sentence. 100.".getBytes(StandardCharsets.UTF_8), "asd");

        MultithreadFileContentFactory fileContentFactory = new MultithreadFileContentFactory(
                threads,
                contents
        );
        List<FileContent> fileContents = fileContentFactory.execute();

        MultithreadFileComparator fileComparator = new MultithreadFileComparator(threads, compared, fileContents);
        List<ComparisonResult> results = fileComparator.execute();

        for(ComparisonResult result : results) {
            System.out.println("Stopień podobieństwa: " + result.getSimilarityDegree());
        }
    }
}