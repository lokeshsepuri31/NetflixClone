package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Titles {

    @SerializedName("results")
    public List<Movies> moviesList = new ArrayList<>();
}
