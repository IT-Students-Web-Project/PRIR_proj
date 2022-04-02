package com.polsl.prir_proj.comparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class MultithreadFileContentFactory {

    ExecutorService executorService;
    private List<FileContent> fileContents;
    private List<GetContentTask> tasks;

    public MultithreadFileContentFactory(int threads, List<byte[]> contents) {
        executorService = Executors.newFixedThreadPool(threads);
        fileContents = new ArrayList<>();
        tasks = new ArrayList<>();
        for(byte[] content : contents)
            tasks.add(new GetContentTask(content));
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

        private byte[] content;

        GetContentTask(byte[] content) {
            this.content = content;
        }

        @Override
        public FileContent call() throws IOException {
            return new FileContent(content);
        }
    }
}
