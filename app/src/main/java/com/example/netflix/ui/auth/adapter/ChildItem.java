package com.example.netflix.ui.auth.adapter;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ChildItem implements Parcelable {

    private final String childItemTitle;

    private final String url,id;

    private Activity activity;

    public ChildItem(String childItemTitle,String url,Activity activity,String id) {
        this.childItemTitle = childItemTitle;
        this.url = url;
        this.activity = activity;
        this.id = id;
    }

    protected ChildItem(Parcel in) {
        childItemTitle = in.readString();
        url = in.readString();
        id = in.readString();
    }

    public static final Creator<ChildItem> CREATOR = new Creator<ChildItem>() {
        @Override
        public ChildItem createFromParcel(Parcel in) {
            return new ChildItem(in);
        }

        @Override
        public ChildItem[] newArray(int size) {
            return new ChildItem[size];
        }
    };

    public String getChildItemTitle() {
        return childItemTitle;
    }

    public String getUrl(){return this.url;}

    public Activity getActivity(){return this.activity;}

    public String getId(){return  this.id;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(childItemTitle);
        dest.writeString(url);
        dest.writeString(id);
    }
}
