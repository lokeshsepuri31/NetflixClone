package com.example.netflix.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_movies")
public class FavoriteMovies {

    @ColumnInfo(name = "fav_id")
    @PrimaryKey(autoGenerate = true)
    private int favId;

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    @ColumnInfo(name = "userId")
    private final int userId;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private final byte[] image;

    @ColumnInfo(name = "movie_title")
    private final String movieTitle;

    public int getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public FavoriteMovies(int userId, String id, byte[] image, String movieTitle) {
        this.userId = userId;
        this.id = id;
        this.image = image;
        this.movieTitle = movieTitle;
    }

}
