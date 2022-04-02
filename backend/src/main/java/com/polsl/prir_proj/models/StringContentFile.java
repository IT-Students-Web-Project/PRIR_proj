package com.polsl.prir_proj.models;

public class StringContentFile {
    public String id;
    public String content;

    public StringContentFile(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public StringContentFile(String id, byte[] content){
        this.id = id;
        this.content = new String(content);
    }

    public StringContentFile() {
    }
}
