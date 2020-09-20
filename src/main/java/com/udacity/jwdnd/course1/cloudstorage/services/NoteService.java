package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.NoteNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    // get all notes
    public List<Note> getAllNotes(User user) {
        return noteMapper.getAllNotes().stream()
                .filter(note -> note.getUserId().equals(user.getUserId())).collect(Collectors.toList());
    }

    // add note
    public void saveNote(String title, String description, Integer userId) {
        Note note = new Note();
        note.setNoteTitle(title);
        note.setNoteDescription(description);
        note.setUserId(userId);
        noteMapper.insert(note);
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
    public Note updateNoteById(Integer noteId, String title, String description) {
        Note noteToBeUpdated = noteMapper.getNoteById(noteId);
        if (noteToBeUpdated == null ) {
            throw new NoteNotFoundException();
        }
        noteToBeUpdated.setNoteTitle(title);
        noteToBeUpdated.setNoteDescription(description);
        noteMapper.update(noteToBeUpdated);
        return noteToBeUpdated;
    }

    // delete note
    public String deleteNoteById(int noteId) {
        int numberOfDeletedNote = noteMapper.deleteNote(noteId);
        return numberOfDeletedNote + " note deleted";
    }
}
