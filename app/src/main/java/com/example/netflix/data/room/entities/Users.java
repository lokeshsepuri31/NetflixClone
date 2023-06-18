package com.example.netflix.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users",indices = {@Index(value = "emailId",unique = true)})
public class Users {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "emailId")
    private String email;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(String email,String password,String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }

}
