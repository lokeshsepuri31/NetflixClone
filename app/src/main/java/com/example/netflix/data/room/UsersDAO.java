package com.example.netflix.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.netflix.data.room.entities.Users;


@Dao
public interface UsersDAO {

    @Query("SELECT id FROM users WHERE username = :username")
    int getUserIdByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username")
    Users getUserByUsername(String username);

    @Insert
    void insertUser(Users user);

}
