package com.example.netflix.data.pojo;

public class Items {

    String movieTitle,url;


    public Items(String movieTitle, String url) {
        this.movieTitle = movieTitle;
        this.url = url;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
