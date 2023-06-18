package com.example.netflix.data.pojo;

public class Items {

    String id;
    String movieTitle;
    byte[] image;


    public Items(String id,String movieTitle, byte[] image) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
