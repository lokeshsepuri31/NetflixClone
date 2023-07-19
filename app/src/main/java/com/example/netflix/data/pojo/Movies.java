package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

public class Movies{
    @SerializedName("id")
    String id;

    @SerializedName("primaryImage")
    PrimaryImage primaryImage = new PrimaryImage();

    @SerializedName("originalTitleText")
    OriginalText originalText = new OriginalText();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OriginalText getOriginalText() {
        return this.originalText;
    }

    public PrimaryImage getPrimaryImage() {
        return this.primaryImage;
    }
}