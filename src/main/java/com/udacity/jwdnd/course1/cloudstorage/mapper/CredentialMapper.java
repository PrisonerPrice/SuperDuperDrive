package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // save
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int save(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(int credentialId);

    // edit
    @Update({"UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, key = #{key}, password = #{password}, userid = #{userId}"})
    int update(Credential credential);

    // remove
    @Delete("DELETE * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(int credentialId);

    // getAll
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getAllCredentials();
}
