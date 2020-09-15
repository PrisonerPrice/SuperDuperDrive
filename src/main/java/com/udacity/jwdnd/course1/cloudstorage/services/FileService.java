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
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    // get all files
    public List<File> getAllFiles(User user) {
        return fileMapper.getAllFiles().stream()
                .filter(file -> file.getUserId().equals(user.getUserId())).collect(Collectors.toList());
    }

    // upload file
    public String uploadFile(MultipartFile multipartFile, User user) {
        String fileName = multipartFile.getName();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        try {
            byte[] fileData = multipartFile.getBytes();
            File newFile = new File(null, fileName, contentType, fileSize, fileData, user.getUserId());
            if (fileMapper.findFileByName(newFile.getFileName()) != null) {
                throw new RepeatNameException();
            }
            fileMapper.insert(newFile);
            return multipartFile.getOriginalFilename() + " is successfully uploaded";
        } catch (IOException | RepeatNameException e) {
            return "Uploading failed";
        }
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
    public String deleteFile(int fileId) {
        int numberOfDeletedFile = fileMapper.deleteFile(fileId);
        return numberOfDeletedFile + " file deleted";
    }
}
