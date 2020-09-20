package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.CredentialAlreadyExistedException;
import com.udacity.jwdnd.course1.cloudstorage.exception.CredentialNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    // get all credentials
    public List<Credential> getAllCredentials(User user) {
        List<Credential> credentials = credentialMapper.getAllCredentials().stream()
                .filter(credential -> credential.getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
        for (Credential credential : credentials) {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(decryptedPassword);
        }
        return credentials;
    }

    // get credential by id
    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    // save
    public String saveCredential(String url, String userName, String password, int userId) {
        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUserName(userName);
        credential.setPassword(password);
        credential.setUserId(userId);
        SecureRandom random = new SecureRandom();
        byte[] k = new byte[16];
        random.nextBytes(k);
        String encodedKey = Base64.getEncoder().encodeToString(k);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.save(credential);
        return "Credential saved successfully";
    }

    // edit
    public Credential updateCredentialById(int credentialId, String url, String userName, String password) {
        if (credentialMapper.getCredentialById(credentialId) == null) {
            throw new CredentialNotFoundException();
        }
        Credential credentialToBeUpdated = credentialMapper.getCredentialById(credentialId);
        credentialToBeUpdated.setUrl(url);
        credentialToBeUpdated.setUserName(userName);
        credentialToBeUpdated.setPassword(password);
        String encodedKey = credentialToBeUpdated.getKey();
        String encryptedPassword = encryptionService.encryptValue(credentialToBeUpdated.getPassword(), encodedKey);
        credentialToBeUpdated.setPassword(encryptedPassword);
        credentialMapper.update(credentialToBeUpdated);
        return credentialToBeUpdated;
    }

    // remove
    public String deleteCredentialById(int credentialId) {
        int numberOfDeletedCredential = credentialMapper.delete(credentialId);
        return numberOfDeletedCredential + " credential deleted";
    }
}
