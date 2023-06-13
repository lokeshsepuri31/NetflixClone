package com.example.netflix.data.pojo;

import android.graphics.Paint;

import com.google.gson.annotations.SerializedName;

public class PrimaryImage{
    @SerializedName("id")
    String id;

    @SerializedName("width")
    int width;

    @SerializedName("height")
    int height;

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

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Caption getCaption() {
        return this.caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }
}
