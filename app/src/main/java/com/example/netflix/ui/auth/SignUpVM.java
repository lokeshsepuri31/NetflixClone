package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

public class SignUpVM extends ViewModel {

    public String email,password,re_password;

    LoginListener loginListener;

    public void onSignUp(View view){
        if(email.isEmpty()||password.isEmpty()||re_password.isEmpty()){
            loginListener.onFailure("Please provide all the credentials");
            return;
        }else{
            loginListener.onSuccess();
        }
    }

}
