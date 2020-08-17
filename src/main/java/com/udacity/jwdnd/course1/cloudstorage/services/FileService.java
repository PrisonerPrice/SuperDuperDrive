package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.exception.RepeatNameException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    // get all files
    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    // upload file
    public String uploadFile(MultipartFile multipartFile, User currUser) {
        String fileName = multipartFile.getName();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        int userId = currUser.getUserId();
        try {
            byte[] fileData = multipartFile.getBytes();
            File newFile = new File(null, fileName, contentType, fileSize, fileData, userId);
            if (fileMapper.findFileByName(newFile.getFileName()) != null) {
                throw new RepeatNameException();
            }
            int numberOfUploadedFile = fileMapper.insert(newFile);
            return numberOfUploadedFile + " file uploaded";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0 file uploaded";
    }

    // download file
    public File downloadFile(int fileId) {
        File file = fileMapper.getFileById(fileId);
        if (file == null) {
            throw new FileNotFoundException();
        }
        return file;
    }

    // delete file
    private String deleteFile(int fileId) {
        int numberOfDeletedFile = fileMapper.deleteFile(fileId);
        return numberOfDeletedFile + " file deleted";
    }
}
