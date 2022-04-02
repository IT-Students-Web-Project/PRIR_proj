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
    byte[] content;

    public File() {
    }

    public File(String id, byte[] content) {
        this.id = id;
        this.content = content;
    }
}
