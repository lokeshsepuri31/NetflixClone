package com.example.netflix.ui.auth;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.Users;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.util.TextValidationUtility;

import java.util.Locale;

public class SignUpVM extends ViewModel {

    public String email="",password="",re_password="";
    LoginListener loginListener;

    DatabaseHandler databaseHandler;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    public void onSignUp(View view){
        if(email.isEmpty() && password.isEmpty() && re_password.isEmpty()){
            loginListener.onFailure("credentials");
        } else if (!password.equals(re_password)) {
            loginListener.onFailure("not same");
        } else if (!TextValidationUtility.emailValidation(email)) {
            loginListener.onFailure("Please provide a valid email.");
        } else if (!TextValidationUtility.passwordLengthValidation(password) && !TextValidationUtility.passwordSpecialCharacterValidation(password)) {
            loginListener.onFailure("Please provide a valid password");
        } else{
            if(NetworkReceiverCallback.isConnection(view.getContext())){
                email = email.toLowerCase(Locale.ROOT);
                String[] emailSplitArray = email.split("@");
                if(databaseCallback.getUserIdByUsername(databaseHandler,emailSplitArray[0]) == 0) {
                    if (databaseCallback.signUp(databaseHandler, new Users(email, password, emailSplitArray[0])))
                        loginListener.onSuccess();
                }else{
                    loginListener.onFailure("This email already exists!");
                }
            }else{
                loginListener.onNetworkError();
            }
        }
    }

}
