package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.NoteNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    // add note
    public String saveNote(Note note) {
        int savedNumberOfNote = noteMapper.insert(note);
        return savedNumberOfNote + " note is saved";
    }

    // get note
    public Note getNoteById(int noteId) {
        Note note = noteMapper.getNoteById(noteId);
        if (note == null) {
            throw new NoteNotFoundException();
        }
        return note;
    }

    // update note
    public String updateNoteById(Note note) {
        int updatedNumberOfNote = noteMapper.update(note);
        return updatedNumberOfNote + " note is updated";
    }

    // delete note
    public String deleteNoteById(int noteId) {
        int deletedNumberOfNote = noteMapper.deleteNote(noteId);
        return deletedNumberOfNote + " note is deleted";
    }
}
