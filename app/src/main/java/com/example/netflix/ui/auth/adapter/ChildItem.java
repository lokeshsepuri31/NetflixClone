package com.example.netflix.ui.auth.adapter;

import android.widget.ImageView;

public class ChildItem {

    private String childItemTitle;


    private String url;

    public ChildItem(String childItemTitle,String url) {
        this.childItemTitle = childItemTitle;
        this.url = url;
    }

    public String getChildItemTitle() {
        return childItemTitle;
    }

    public String getUrl(){return this.url;}
}
