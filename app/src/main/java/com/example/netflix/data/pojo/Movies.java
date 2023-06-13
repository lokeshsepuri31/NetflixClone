package com.example.netflix.data.pojo;

import com.google.gson.annotations.SerializedName;

public class Movies{
    @SerializedName("id")
    String id;

    @SerializedName("primaryImage")
    PrimaryImage primaryImage = new PrimaryImage();

    @SerializedName("titleType")
    TitleType titleType = new TitleType();

    @SerializedName("releaseYear")
    ReleaseYear releaseYear = new ReleaseYear();

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

    public void setPrimaryImage(PrimaryImage primaryImage) {
        this.primaryImage = primaryImage;
    }

    public TitleType getTitleType() {
        return this.titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }

    public ReleaseYear getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(ReleaseYear releaseYear) {
        this.releaseYear = releaseYear;
    }
}