package com.example.netflix.ui.auth.adapter;

import android.app.Activity;

public class SearchItem{
    private String title;

    private String url,id;

    private Activity activity;

    public Activity getActivity() {
        return this.activity;
    }

    public String getId() {
        return id;
    }

    public SearchItem(String title, String url, Activity activity, String id) {
        this.title = title;
        this.url = url;
        this.activity = activity;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl(){
        return this.url;
    }
}
