package com.udacity.jwdnd.course1.cloudstorage.exception;

public class BaseException extends RuntimeException{
    private String exceptionCode;
    private String exceptionMessage;

    public BaseException(String exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

}
