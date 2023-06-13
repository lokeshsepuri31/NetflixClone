package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

public class OriginalText {

    @SerializedName("text")
    String movieName;

    public String getMovieName() {
        return movieName;
    }
}
