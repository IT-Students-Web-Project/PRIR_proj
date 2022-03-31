package com.polsl.prir_proj.comparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadFileComparator {

    ExecutorService executorService;
    private List<ComparisonResult> results;
    private List<ComparisonTask> tasks;
    private List<File> files;
    private int threads;
    private File compared;

    public MultithreadFileComparator(int threads, File compared, List<File> files) {
        this.threads = threads;
        this.compared = compared;
        this.files = files;
        results = new ArrayList<>();
        tasks = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(threads);
    }

    public List<ComparisonResult> execute() throws InterruptedException, FileNotFoundException {
        List<FileContent> fileContents = prepareFileContents(threads, files);
        FileContent comparedContent = new FileContent(compared);
        for(FileContent original : fileContents)
            tasks.add(new ComparisonTask(original, comparedContent));

        List<ComparisonResult> results = new ArrayList<>();
        List<Future<ComparisonResult>> futureResults = executorService.invokeAll(tasks);
        executorService.shutdown();

        for(Future<ComparisonResult> future : futureResults) {
            try {
                results.add(future.get());
            } catch(ExecutionException e) {
                System.err.println("Nie udało się wykonać porównania");
            }
        }
        return results;
    }

    private List<FileContent> prepareFileContents(int threads, List<File> files) throws InterruptedException {
        MultithreadFileContentFactory fileContentFactory = new MultithreadFileContentFactory(
                threads,
                files
        );
        return fileContentFactory.execute();
    }

    class ComparisonTask implements Callable<ComparisonResult> {

        private FileContent original;
        private FileContent compared;

        ComparisonTask(FileContent original, FileContent compared) {
            this.original = original;
            this.compared = compared;
        }

        @Override
        public ComparisonResult call() {
            FileComparator fileComparator = new FileComparator();
            return fileComparator.compareForSentences(original, compared);
        }
    }
}
