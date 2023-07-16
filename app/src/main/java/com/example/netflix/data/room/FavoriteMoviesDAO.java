package com.example.netflix.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.netflix.data.room.entities.FavoriteMovies;

import java.util.List;

@Dao
public interface FavoriteMoviesDAO {

    @Query("SELECT * FROM favorite_movies WHERE userId = :id")
    List<FavoriteMovies> getAllFavMoviesOfLoggedInUser(int id);

    @Insert
    void insertFavMovie(FavoriteMovies favoriteMovies);

    @Query("DELETE FROM favorite_movies WHERE userId = :userId AND id = :id")
    int deleteFavMovie(int userId,String id);

    @Query("SELECT * FROM favorite_movies WHERE id = :id AND userId = :userId")
    FavoriteMovies getFavMovieOfLoggedInUser(String id,int userId);
}
