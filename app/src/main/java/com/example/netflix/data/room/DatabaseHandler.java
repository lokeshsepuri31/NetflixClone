package com.example.netflix.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.data.room.entities.Users;

@Database(entities = {Users.class, FavoriteMovies.class}, exportSchema = false,version = 1)
public abstract class DatabaseHandler extends RoomDatabase {

    private static final String DB_NAME = "netflix_db";
    private static DatabaseHandler instance;

    public static synchronized DatabaseHandler getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),DatabaseHandler.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract FavoriteMoviesDAO favoriteMoviesDAO();
    public abstract UsersDAO usersDAO();

}
