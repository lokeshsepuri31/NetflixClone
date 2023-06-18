package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.Users;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.util.TextValidationUtility;

public class SignUpVM extends ViewModel {

    public String email="",password="",re_password="";
    LoginListener loginListener;

    DatabaseHandler databaseHandler;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    public void onSignUp(View view){
        if(email.isEmpty()||password.isEmpty()||re_password.isEmpty()){
            loginListener.onFailure("Please provide all the credentials");
            return;
        } else if (!password.equals(re_password)) {
            loginListener.onFailure("Password and Confirm Password or not same");
        } else{
            if(NetworkReceiverCallback.isConnection(view.getContext())){
                String[] emailSplitArray = email.split("@");
                if (databaseCallback.signUp(databaseHandler,new Users(email,password,emailSplitArray[0])))
                    loginListener.onSuccess();
            }else{
                loginListener.onNetworkError();
            }
        }
    }

}
