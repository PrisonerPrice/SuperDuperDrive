package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/file"})
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/all")
    public ResponseEntity<List<Resource>> getAllFiles(User user, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            List<Resource> filesResources = fileService.getAllFiles(user).stream().map(file -> new ByteArrayResource(file.getFileData())).collect(Collectors.toList());
            //body(new ByteArrayResource(dbFile.getData()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("file"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachments")
                    .body(filesResources);
        } else {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> uploadFile(MultipartFile file, Authentication authentication, User user) {
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(fileService.uploadFile(file, user));
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }
    
    @DeleteMapping("/")
    public ResponseEntity<String> deleteFile(int fileId, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(fileService.deleteFile(fileId));
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }

}
