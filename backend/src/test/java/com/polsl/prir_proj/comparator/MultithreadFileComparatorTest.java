package com.polsl.prir_proj.comparator;

import com.polsl.prir_proj.models.File;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class MultithreadFileComparatorTest {

    @Test
    void shouldCompareMultipleFilesToOriginal() throws InterruptedException {
        int threads = Runtime.getRuntime().availableProcessors();

        List<File> files = prepareTestFiles();


        FileContent compared = new FileContent(
                "Test. New kind of test. Next sentence. 100.".getBytes(StandardCharsets.UTF_8),
                "asd",
                "plik porównywany"
        );

        MultithreadFileContentFactory fileContentFactory = new MultithreadFileContentFactory(
                threads,
                files
        );
        List<FileContent> fileContents = fileContentFactory.execute();

        MultithreadFileComparator fileComparator = new MultithreadFileComparator(threads, compared, fileContents);
        List<ComparisonResult> results = fileComparator.execute();

        for(ComparisonResult result : results) {
            System.out.println("Stopień podobieństwa: " + result.getSimilarityDegree());
        }
    }

    private List<File> prepareTestFiles() {
        List<File> files = new ArrayList<>();
        for(int i = 1; i <5; i++) {
            File file = new File(
                    "Test " + i,
                    "Test. New kind of test. Next sentence.".getBytes(StandardCharsets.UTF_8),
                    null
            );
            file.setId("T" + i);
            files.add(file);
        }

        File file = new File(
                "test",
                "Test. New kind of test. Next sentence. 100.".getBytes(StandardCharsets.UTF_8),
                null
        );
        file.setId("test");
        files.add(file);
        return files;
    }
}