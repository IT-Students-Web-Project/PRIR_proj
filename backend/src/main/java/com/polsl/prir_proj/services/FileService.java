package com.polsl.prir_proj.services;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.comparator.FileContent;
import com.polsl.prir_proj.comparator.MultithreadFileComparator;
import com.polsl.prir_proj.comparator.MultithreadFileContentFactory;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.models.StringContentFile;
import com.polsl.prir_proj.repositories.FileRepository;
import com.polsl.prir_proj.web.dto.FileInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

    public List<StringContentFile> getAllFilesString() {
        List<StringContentFile> stringContentFiles = new ArrayList<>();
        for (File file : getAllFiles()) {
            stringContentFiles.add(new StringContentFile(file.getId(), new String(file.getContent())));
        }
        return stringContentFiles;
    }

    public String addFile(File file) {
        File saved = this.fileRepository.save(file);
        return saved.getId();
    }

    public File getFileById(String id) {
        Optional<File> file = this.fileRepository.findById(id);
        return file.orElse(null);
    }

    public void removeFile(String id) {
        this.fileRepository.deleteById(id);
    }

    public void editFile(File file) {
        if (this.fileRepository.findById(file.getId()).isPresent()) {
            this.fileRepository.deleteById(file.getId());
            this.fileRepository.save(file);
        }
    }

    public List<ComparisonResult> getSimilarFromAll(String id) {
        List<ComparisonResult> comparisonResults = compareToAll(id);
        return comparisonResults.stream()
                .filter(r -> r.getSimilarityDegree() > 0)
                .collect(Collectors.toList());
    }

    public List<ComparisonResult> compareToAll(String id) {
        int threads = Runtime.getRuntime().availableProcessors();

        List<File> files = getAllFiles();
        File comparedFile = getFileById(id);
        files.remove(comparedFile);

        FileContent compared = new FileContent(
                comparedFile.getContent(),
                comparedFile.getId(),
                comparedFile.getFileName()
        );

        MultithreadFileContentFactory fileContentFactory = new MultithreadFileContentFactory(
                threads,
                files
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

    public void updateFileInfo(FileInfoDTO fileInfoDTO) throws Exception {
        String id = fileInfoDTO.getId();
        Optional<File> fileOptional = fileRepository.findById(id);

        File file;
        if(fileOptional.isPresent())
            file = fileOptional.get();
        else
            throw new Exception("Brak pliku o id: " + id + " w bazie danych");

        file.setFileName(fileInfoDTO.getFileName());
        file.setUser(fileInfoDTO.getUser());
        fileRepository.save(file);
    }

    public List<StringContentFile> getAllFilesByUser(String id) {
        List<StringContentFile> stringContentFiles = new ArrayList<>();
        for (File file : getAllFilesByUserId(id)) {
            stringContentFiles.add(new StringContentFile(file.getId(), new String(file.getContent()), file.getFileName()));
        }
        return stringContentFiles;
    }

    private List<File> getAllFilesByUserId(String id) {
        List<File> userFiles = new ArrayList<>();
        for(File file: this.fileRepository.findAll()){
            if(file.getUser() != null && file.getUser().equals(id)){
                userFiles.add(file);
            }
        }
        return userFiles;
    }
}
