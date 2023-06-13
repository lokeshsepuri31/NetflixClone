package com.example.netflix.ui.auth.adapter;

import android.widget.ImageView;
import android.widget.TextView;

public class SearchItem {
    private String title;

    private String url;

    public SearchItem(String title,String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl(){
        return this.url;
    }
}
