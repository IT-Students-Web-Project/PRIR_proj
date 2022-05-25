package com.polsl.prir_proj.models;

public class StringContentFile {
    public String id;
    public String content;
    public String name;

    public StringContentFile(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public StringContentFile(String id, byte[] content){
        this.id = id;
        this.content = new String(content);
    }

    public StringContentFile(String id, String content, String name){
        this.id = id;
        this.content = content;
        this.name = name;
    }

    public StringContentFile() {
    }
}
