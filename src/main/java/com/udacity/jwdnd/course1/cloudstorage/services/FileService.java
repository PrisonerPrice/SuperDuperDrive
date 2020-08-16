package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.RepeatNameException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

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
                throw new RepeatNameException("0001", "File name already existed, please upload file with other names");
            }
            int returnValue = fileMapper.insert(newFile);
            return returnValue + " file uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0 file uploaded successfully!";
    }

    // download file
    public File downloadFile(int fileId) {
        return null;
    }

    // delete file
    private String deleteFIle(int fileId) {
        return null;
    }
}
