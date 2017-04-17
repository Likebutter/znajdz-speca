package com.example.Photo;


public class FileData {

    private byte[] content;
    private String extension;

    public FileData() {
    }

    public FileData(byte[] content, String extension) {
        this.content = content;
        this.extension = extension;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
