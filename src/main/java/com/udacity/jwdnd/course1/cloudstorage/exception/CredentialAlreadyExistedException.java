package com.udacity.jwdnd.course1.cloudstorage.exception;

public class CredentialAlreadyExistedException extends BaseException{
    public CredentialAlreadyExistedException() {
        super("0005", "Credential already existed!");
    }
}
