package com.example.netflix.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.netflix.data.room.entities.FavoriteMovies;

import java.util.List;

@Dao
public interface FavoriteMoviesDAO {

    @Query("SELECT * FROM favorite_movies WHERE userId = :id")
    List<FavoriteMovies> getAllFavMoviesofLoggedInUser(int id);

    @Insert
    void insertFavMovie(FavoriteMovies favoriteMovies);

    @Update
    void updateFavMovie(FavoriteMovies favoriteMovies);

    @Query("DELETE FROM favorite_movies WHERE userId = :userId AND id = :id")
    int deleteFavMovie(int userId,String id);

    @Query("SELECT * FROM favorite_movies WHERE id = :id AND userId = :userId")
    FavoriteMovies getFavMovieofLoggedInUser(String id,int userId);
}
