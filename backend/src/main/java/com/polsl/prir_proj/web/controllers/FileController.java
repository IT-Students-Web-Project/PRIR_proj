package com.polsl.prir_proj.web.controllers;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.models.StringContentFile;
import com.polsl.prir_proj.services.FileService;
import com.polsl.prir_proj.web.dto.FileInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(@Autowired FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("compareToAll/{id}")
    public List<ComparisonResult> GetComparisonToAll(@PathVariable("id") String id) {
        return fileService.getSimilarFromAll(id);
    }

    @GetMapping("fileList")
    public List<File> GetFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("fileListString")
    public List<StringContentFile> GetFilesString() {
        return fileService.getAllFilesString();
    }

    @GetMapping("fileListStringByUser/{id}")
    public List<StringContentFile> GetFilesString(@PathVariable("id")String id) { return fileService.getAllFilesByUser(id);}

    @GetMapping("file/{id}")
    public File GetFile(@PathVariable("id") String id) {
        return fileService.getFileById(id);
    }

    @PostMapping("addFile")
    public String PostFile(@RequestBody MultipartFile document) throws IOException {
        File file = new File(
                document.getOriginalFilename(),
                document.getBytes(),
                null
        );
        return fileService.addFile(file);
    }

    @PutMapping("updateFileInfo")
    public void updateFileInfo(@RequestBody FileInfoDTO fileInfoDTO) throws Exception {
        fileService.updateFileInfo(fileInfoDTO);
    }

    @DeleteMapping("removeFile/{id}")
    public void RemoveFile(@PathVariable("id") String id) {
        fileService.removeFile(id);
    }
}
