package com.example.netflix.data.pojo;


import com.google.gson.annotations.SerializedName;

public class PrimaryImage{

    @SerializedName("width")
    int width;

    @SerializedName("url")
    String url;

    @SerializedName("caption")
    Caption caption = new Caption();




    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return this.url;
    }

    public Caption getCaption() {
        return this.caption;
    }
}
