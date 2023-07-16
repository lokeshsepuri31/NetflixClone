package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.netflix.R;

import java.util.Timer;
import java.util.TimerTask;

public class SuccessActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        TextView textView = findViewById(R.id.message);
        textView.setText(getIntent().getStringExtra("message"));
        login = findViewById(R.id.login_page);
        login.setOnClickListener((view)->{
            onBackPressed();
        });
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
        }, 10, 2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
    }
}