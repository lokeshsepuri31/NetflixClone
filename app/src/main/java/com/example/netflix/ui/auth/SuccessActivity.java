package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.netflix.R;

import java.util.Timer;
import java.util.TimerTask;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        TextView textView = findViewById(R.id.message);
        textView.setText(getIntent().getStringExtra("message"));
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1, 2000);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(LoginActivity.ISLOGIN,true).commit();

        startActivity(new Intent(this,HomeActivity.class));
    }
}