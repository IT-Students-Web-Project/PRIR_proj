package com.polsl.prir_proj.services;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.comparator.MultithreadFileComparator;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final FileRepository fileRepository;

    public List<File> getAllFiles() {
        return this.fileRepository.findAll();
    }

    public void AddFile(File file){
        this.fileRepository.save(file);
    }

    public File getFileById(String id){
        Optional<File> file = this.fileRepository.findById(id);
        return file.orElse(null);
    }

    public void removeFile(String id){
        this.fileRepository.deleteById(id);
    }

    public List<ComparisonResult> compareFileToDatabase(File file) throws FileNotFoundException, InterruptedException {
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
