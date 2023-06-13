package com.example.netflix.ui.auth.listeners;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getItemId());
        return false;
    }
}
