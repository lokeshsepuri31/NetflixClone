package com.example.netflix.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.netflix.data.room.entities.Users;


@Dao
public interface UsersDAO {
    @Query("SELECT * FROM users WHERE id = :id")
    Users getUserById(int id);

    @Query("SELECT id FROM users WHERE username = :username")
    int getUserIdByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username")
    Users getUserByUsername(String username);

    @Insert
    void insertUser(Users user);

    @Delete
    void deleteUser(Users user);

    @Update
    void updateUser(Users users);

}
