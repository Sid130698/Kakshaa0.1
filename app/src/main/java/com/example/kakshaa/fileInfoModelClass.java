package com.example.kakshaa;

public class fileInfoModelClass {
    String fileName,fileURL;
    public fileInfoModelClass(String fileName, String fileURL) {
        this.fileName = fileName;
        this.fileURL = fileURL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }



}
