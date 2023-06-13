package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.netflix.R;

public class WatchNowActivity extends AppCompatActivity {

    HomeVM homeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_now);
        homeVM = new ViewModelProvider(this).get(HomeVM.class);
        System.out.println(homeVM.upcomingMovies);
    }
}