package com.udacity.jwdnd.course1.cloudstorage.exception;

public class RepeatNameException extends IllegalArgumentException{
    private String exceptionCode;
    private String exceptionMessage;

    public RepeatNameException(String exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

}
