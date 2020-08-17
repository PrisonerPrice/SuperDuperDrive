package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    // upload
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    // download
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(int fileId);

    // remove
    @Delete("DELETE * FROM FILES WHERE fileid = #{fileId}")
    int deleteFile(int fileId);

    // getFileByName
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File findFileByName(String fileName);

    // get all files
    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();
}
