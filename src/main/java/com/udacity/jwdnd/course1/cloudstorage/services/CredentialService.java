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

    // save
    public String saveCredential(Credential credential) {
        if (credential.getCredentialId() != null) {
            throw new CredentialAlreadyExistedException();
        }
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        int numberOfSavedCredential = credentialMapper.save(credential);
        return numberOfSavedCredential + " credential saved";
    }

    // edit
    public Credential updateCredential(Credential credential) {
        if (credential.getCredentialId() == null) {
            throw new CredentialNotFoundException();
        }
        credentialMapper.update(credential);
        return credentialMapper.getCredentialById(credential.getCredentialId());
    }

    // remove
    public String deleteCredential(int credentialId) {
        int numberOfDeletedCredential = credentialMapper.delete(credentialId);
        return numberOfDeletedCredential + " credential deleted";
    }
}
