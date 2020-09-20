package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/note"})
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> createNewNote(@RequestParam String title, @RequestParam String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        if (authentication.isAuthenticated()) {
            noteService.saveNote(title, description, user.getUserId());
            return new ResponseEntity<>("Save note successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(noteService.getAllNotes(user));
        } else {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@RequestParam("id") int noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(noteService.getNoteById(noteId));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNoteById(@RequestParam("id") int noteId, @RequestParam String title, @RequestParam String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(noteService.updateNoteById(noteId, title, description));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNoteById(@RequestParam("id") int noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            noteService.deleteNoteById(noteId);
            return ResponseEntity.ok().body("Delete successfully");
        } else {
            return ResponseEntity.badRequest().body("Not authenticated");
        }
    }
}
