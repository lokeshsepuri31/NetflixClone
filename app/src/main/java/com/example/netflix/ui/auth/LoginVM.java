package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.Users;
import com.example.netflix.util.NetworkReceiverCallback;

import java.util.Locale;

public class LoginVM extends ViewModel {

    public String username = "", password = "";

    public int userId = 0;

    public LoginListener loginListener;

    public DatabaseHandler databaseHandler;


    private DatabaseCallback databaseCallback = new DatabaseCallback();

    public void onLogin(View view) {
        if (username.isEmpty() && password.isEmpty()) {
            loginListener.onFailure("both");
            return;
        } else if (password.isEmpty() && !username.isEmpty()) {
            loginListener.onFailure(" Password");
            return;
        } else if (username.isEmpty() && !password.isEmpty()) {
            loginListener.onFailure(" Username");
            return;
        } else {
            if (NetworkReceiverCallback.isConnection(view.getContext())) {
                username = username.toLowerCase(Locale.ROOT);
                Users users = databaseCallback.logIn(databaseHandler, username);
                if (users != null && users.getPassword().equals(password)) {
                    userId = users.getId();
                    loginListener.onSuccess();
                }
                else
                    loginListener.onFailure("Username or Password Incorrect!");
            } else
                loginListener.onNetworkError();
        }
    }
}
