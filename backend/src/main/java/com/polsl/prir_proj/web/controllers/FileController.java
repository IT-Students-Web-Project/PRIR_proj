package com.polsl.prir_proj.web.controllers;

import com.polsl.prir_proj.comparator.ComparisonResult;
import com.polsl.prir_proj.models.File;
import com.polsl.prir_proj.models.StringContentFile;
import com.polsl.prir_proj.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("similarityDegreeToAll/{id}")
    public List<ComparisonResult> GetComparisonToAll(@PathVariable("id") String id){
        return fileService.CompareToAll(id);
    }

    @GetMapping("fileList")
    public List<File> GetFiles(){
        return fileService.GetAllFiles();
    }

    @GetMapping("fileListString")
    public List<StringContentFile> GetFilesString() {return fileService.GetAllFilesString();}

    @GetMapping("file/{id}")
    public File GetFile(@PathVariable("id") String id){
        return fileService.GetFileById(id);
    }

    @PostMapping("addFileString")
    public void PostFileWithStringContent(@RequestBody StringContentFile file){
        fileService.AddFileString(file);
    }

    @PostMapping("addFile")
    public void PostFile(@RequestBody File file){
        fileService.AddFile(file);
    }

    @DeleteMapping("removeFile/{id}")
    public void RemoveFile(@PathVariable("id") String id){
        fileService.RemoveFile(id);
    }

    @PutMapping("editFile")
    public void EditFileWithStringContent(@RequestBody StringContentFile file){
        fileService.EditFileString(file);
    }
}
