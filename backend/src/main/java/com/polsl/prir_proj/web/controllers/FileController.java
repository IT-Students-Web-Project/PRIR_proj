package com.polsl.prir_proj.web.controllers;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.comparator.MultithreadFileComparator;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("compareFile")
    public Iterable<ComparisonResult> compareFileToDatabase(File file) throws Exception {
        int threads = Runtime.getRuntime().availableProcessors();
        java.io.File compared = new java.io.File(file.getFilePath());

        List<java.io.File> database = fileRepository.findAll()
                .stream()
                .map(f -> new java.io.File(f.getFilePath()))
                .collect(Collectors.toList());

        MultithreadFileComparator comparator = new MultithreadFileComparator(
                threads,
                compared,
                database
        );
        return comparator.execute();
    }
}
