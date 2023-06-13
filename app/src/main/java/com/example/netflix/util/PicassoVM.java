package com.example.netflix.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PicassoVM extends ViewModel {
    ImageView imageView;
    public void setLoginImage(ImageView imageView,String url){
        Picasso.get().load(url).into(imageView);
    }
}
