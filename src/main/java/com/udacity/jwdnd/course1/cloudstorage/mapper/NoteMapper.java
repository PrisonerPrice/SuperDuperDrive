package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    // add note
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    // update note
    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId}")
    int update(Note note);

    // get note
    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
    Note getNote(String noteTitle);
    @Select("SELECT * FROM NOTES")
    List<Note> getNotes();

    // remove note
    @Delete("DELETE * FROM NOTES WHERE noteid = #{noteId}")
    int deleteNote(int noteId);
}
