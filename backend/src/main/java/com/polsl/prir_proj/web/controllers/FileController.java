package com.polsl.prir_proj.web.controllers;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("fileList")
    public List<File> GetFiles(){
        return fileService.getAllFiles();
    }
    @GetMapping("file/{id}")
    public File GetFile(@PathVariable("id") String id){
        return fileService.getFileById(id);
    }
    @PostMapping("addFile")
    public void PostFile(@RequestBody File file){
        fileService.AddFile(file);
    }
    @DeleteMapping("removeFile/{id}")
    public void RemoveFile(@PathVariable("id") String id){
        fileService.removeFile(id);
    }

    @PostMapping("compareFile")
    public Iterable<ComparisonResult> compareFileToDatabase(@RequestBody File file) throws Exception {
        return fileService.compareFileToDatabase(file);
    }
}
