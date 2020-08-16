package com.udacity.jwdnd.course1.cloudstorage.exception;

public class RepeatNameException extends BaseException{

    public RepeatNameException() {
        super("0001", "File name already existed, please upload file with other names");
    }

}
