package com.polsl.prir_proj.services;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.comparator.FileContent;
import com.polsl.prir_proj.comparator.MultithreadFileComparator;
import com.polsl.prir_proj.comparator.MultithreadFileContentFactory;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.models.StringContentFile;
import com.polsl.prir_proj.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final FileRepository fileRepository;

    public List<File> GetAllFiles() {
        return this.fileRepository.findAll();
    }

    public List<StringContentFile> GetAllFilesString() {
        List<StringContentFile> stringContentFiles = new ArrayList<>();
        for(File file : GetAllFiles()){
            stringContentFiles.add(new StringContentFile(file.getId(), new String(file.getContent())));
        }
        return  stringContentFiles;
    }

    public void AddFile(File file){
        this.fileRepository.save(file);
    }

    public void AddFileString(StringContentFile file, String username){
        AddFile(new File(file.id, file.content.getBytes(StandardCharsets.UTF_8), username));
    }

    public File GetFileById(String id){
        Optional<File> file = this.fileRepository.findById(id);
        return file.orElse(null);
    }

    public void RemoveFile(String id){
        this.fileRepository.deleteById(id);
    }

    public void EditFile(File file){
        if(this.fileRepository.findById(file.getId()).isPresent()){
            this.fileRepository.deleteById(file.getId());
            this.fileRepository.save(file);
        }
    }

    public void EditFileString(StringContentFile file){
        EditFile(new File(file.id, file.content.getBytes(StandardCharsets.UTF_8)));
    }

    public List<ComparisonResult> CompareToAll(String id){
        int threads = Runtime.getRuntime().availableProcessors();

        List<File> files = GetAllFiles();
        File comparedFile = GetFileById(id);
        files.remove(comparedFile);
        HashMap<String, byte[]> contents = new HashMap<>();
        for(File file : files) {
            contents.put(file.getId(), file.getContent());
        }
        FileContent compared = new FileContent(comparedFile.getContent(), comparedFile.getId());

        MultithreadFileContentFactory fileContentFactory = new MultithreadFileContentFactory(
                threads,
                contents
        );
        List<ComparisonResult> results;
        try {
            List<FileContent> fileContents = fileContentFactory.execute();

            MultithreadFileComparator fileComparator = new MultithreadFileComparator(threads, compared, fileContents);
            results = fileComparator.execute();
            return results;
        } catch (InterruptedException e) {
            return null;
        }

    }
}
