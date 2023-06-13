package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

public class Caption{

    @SerializedName("plainText")
    String movieName;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
