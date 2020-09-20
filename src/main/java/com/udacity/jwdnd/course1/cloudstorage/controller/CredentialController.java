package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/credential"})
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> createNewCredential(
            @RequestParam String url,
            @RequestParam String userName,
            @RequestParam String key,
            @RequestParam String password
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser((String) authentication.getPrincipal());
        if (authentication.isAuthenticated()) {
            credentialService.saveCredential(url, userName, password, user.getUserId());
            return new ResponseEntity<>("Save note successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Credential>> getAllCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(credentialService.getAllCredentials(user));
        } else {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credential> getCredentialById(@RequestParam("id") int credentialId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(credentialService.getCredentialById(credentialId));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Credential> updateCredentialById(
            @RequestParam("id") int credentialId,
            @RequestParam String url,
            @RequestParam String userName,
            @RequestParam String password
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(credentialService.updateCredentialById(credentialId, url, userName, password));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCredentialById(@RequestParam("id") int credentialId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            credentialService.deleteCredentialById(credentialId);
            return ResponseEntity.ok().body("Delete successfully");
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }
}
