package com.udacity.jwdnd.course1.cloudstorage.model;


public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private byte[] fileDate;

    public File(Integer fileId, String fileName, String contentType, String fileSize, byte[] fileDate) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileDate = fileDate;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileDate() {
        return fileDate;
    }

    public void setFileDate(byte[] fileDate) {
        this.fileDate = fileDate;
    }
}
