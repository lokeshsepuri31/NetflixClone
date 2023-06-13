package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

public class TitleType {

    @SerializedName("text")
    String text;

    @SerializedName("id")
    String id;

    @SerializedName("isSeries")
    boolean isSeries;

    @SerializedName("isEpisode")
    boolean isEpisode;
}
