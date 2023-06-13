package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements LoginListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        SignUpVM signUpVM = new ViewModelProvider(this).get(SignUpVM.class);
        signUpVM.loginListener = this;
        binding.setViewModel(signUpVM);
        getSupportActionBar().hide();
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(this,SuccessActivity.class);
        intent.putExtra("message","Your Account is ready!");
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}