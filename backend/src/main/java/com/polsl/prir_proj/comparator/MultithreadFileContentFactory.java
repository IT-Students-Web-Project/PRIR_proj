package com.polsl.prir_proj.comparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadFileContentFactory {

    ExecutorService executorService;
    private List<FileContent> fileContents;
    private List<GetContentTask> tasks;

    public MultithreadFileContentFactory(int threads, List<File> files) {
        executorService = Executors.newFixedThreadPool(threads);
        fileContents = new ArrayList<>();
        tasks = new ArrayList<>();
        for(File file : files)
            tasks.add(new GetContentTask(file));
    }

    public List<FileContent> execute() throws InterruptedException {
        List<FileContent> results = new ArrayList<>();
        List<Future<FileContent>> futureResults = executorService.invokeAll(tasks);
        executorService.shutdown();

        for(Future<FileContent> future : futureResults) {
            try {
                results.add(future.get());
            } catch(ExecutionException e) {
                System.err.println("Nie udało się wykonać GetContentTask");
                System.err.println(e.getCause());
            }
        }
        return results;
    }

    class GetContentTask implements Callable<FileContent> {

        private File file;

        GetContentTask(File file) {
            this.file = file;
        }

        @Override
        public FileContent call() throws FileNotFoundException {
            return new FileContent(file);
        }
    }
}
