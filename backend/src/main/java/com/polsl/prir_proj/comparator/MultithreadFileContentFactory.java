package com.polsl.prir_proj.comparator;

import com.polsl.prir_proj.models.File;

import java.util.*;
import java.util.concurrent.*;

public class MultithreadFileContentFactory {

    ExecutorService executorService;
    private List<GetContentTask> tasks;

    public MultithreadFileContentFactory(int threads, List<File> files) {
        executorService = Executors.newFixedThreadPool(threads);
        tasks = new ArrayList<>();
        for (File file : files)
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

        private byte[] content;
        private String fileId;
        private String fileName;

        GetContentTask(File file) {
            this.content = file.getContent();
            this.fileId = file.getId();
            this.fileName = file.getFileName();
        }

        @Override
        public FileContent call() {
            return new FileContent(content, fileId, fileName);
        }
    }
}
