package com.polsl.prir_proj.comparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadFileContentFactory {

    ExecutorService executorService;
    private List<FileContent> fileContents;
    private List<GetContentTask> tasks;

    public MultithreadFileContentFactory(int threads, HashMap<String, byte[]> contents) {
        executorService = Executors.newFixedThreadPool(threads);
        fileContents = new ArrayList<>();
        tasks = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry : contents.entrySet())
            tasks.add(new GetContentTask(entry.getValue(), entry.getKey()));
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
        private String fileId;

        GetContentTask(byte[] content, String fileId) {
            this.content = content;
            this.fileId = fileId;
        }

        @Override
        public FileContent call() throws IOException {
            return new FileContent(content, fileId);
        }
    }
}
