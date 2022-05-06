package com.polsl.prir_proj.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "file")
public class File {

    @Id
    String id;

    @Field
    String fileName;

    @Field
    byte[] content;

    @Field
    String user;

    public File() {
    }

    public File(String fileName, byte[] content, String username){
        this.fileName = fileName;
        this.content = content;
        this.user = username;
    }
}
