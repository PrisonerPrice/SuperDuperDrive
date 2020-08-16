package com.udacity.jwdnd.course1.cloudstorage.exception;

public class NoteNotFoundException extends BaseException{

    public NoteNotFoundException() {
        super("0003", "Note not found!");
    }

}
