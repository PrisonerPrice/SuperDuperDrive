package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.NoteNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    // get all notes
    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    // add note
    public String saveNote(Note note) {
        int numberOfSavedNote = noteMapper.insert(note);
        return numberOfSavedNote + " note saved";
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
    public Note updateNoteById(Note note) {
        if (note.getNoteId() == null) {
            throw new NoteNotFoundException();
        }
        noteMapper.update(note);
        return noteMapper.getNoteById(note.getNoteId());
    }

    // delete note
    public String deleteNoteById(int noteId) {
        int numberOfDeletedNote = noteMapper.deleteNote(noteId);
        return numberOfDeletedNote + " note deleted";
    }
}
