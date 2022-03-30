package com.polsl.prir_proj.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadFileComparator {

    ExecutorService executorService;
    private List<ComparisonResult> results;
    private List<ComparisonTask> tasks;

    public MultithreadFileComparator(int threads, FileContent compared, List<FileContent> database) {
        executorService = Executors.newFixedThreadPool(threads);
        results = new ArrayList<>();
        tasks = new ArrayList<>();
        for(FileContent original : database)
            tasks.add(new ComparisonTask(original, compared));
    }

    public List<ComparisonResult> execute() throws InterruptedException {
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
