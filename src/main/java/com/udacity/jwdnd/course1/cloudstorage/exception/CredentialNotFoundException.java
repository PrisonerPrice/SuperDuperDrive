package com.udacity.jwdnd.course1.cloudstorage.exception;

public class CredentialNotFoundException extends BaseException{

    public CredentialNotFoundException() {
        super("0004", "Credential not found!");
    }
}
