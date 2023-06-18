package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.Users;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.util.TextValidationUtility;

public class LoginVM extends ViewModel {

    public String username = "", password = "";

    public LoginListener loginListener;

    public DatabaseHandler databaseHandler;


    private DatabaseCallback databaseCallback = new DatabaseCallback();

    public void onLogin(View view) {
        if (username.isEmpty() || password.isEmpty()) {
            loginListener.onFailure("Please provide the Username or Password");
            return;
        } else {
            if (NetworkReceiverCallback.isConnection(view.getContext())) {
                Users users = databaseCallback.logIn(databaseHandler, username);
                if (users != null && users.getPassword().equals(password))
                    loginListener.onSuccess();
                else
                    loginListener.onFailure("Username or Password Incorrect !");
            } else
                loginListener.onNetworkError();
        }
    }
}
