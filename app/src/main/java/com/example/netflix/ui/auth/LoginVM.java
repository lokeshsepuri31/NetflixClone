package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginVM extends ViewModel {

    public String username,password;

    public LoginListener loginListener;

    public void onLogin(View view){
        if(username.isEmpty()||password.isEmpty()){
            loginListener.onFailure("Please provide the Username or Password");
            return;
        }else{
            if(validate(password))
                loginListener.onSuccess();
            else
                loginListener.onFailure("Password Incorrect!!");
        }
    }

    public boolean validate(String password){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if(password.length() >8 && isStringContainsSpecialCharacter)
            return true;
        else return false;
    }

}
