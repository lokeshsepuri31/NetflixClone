package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String ISLOGIN = "IsLogin";
    public static final String LOGIN_USERNAME = "loggedInUsername";
    public static final String LOGIN_USERID = "loggedInUserId";
    LoginVM loginVM;

    int backCounts = 0;

    TextView signup;

    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginVM = new ViewModelProvider(this).get(LoginVM.class);
        loginVM.loginListener = this;
        loginVM.databaseHandler = DatabaseHandler.getInstance(this);
        findViewById(R.id.username).requestFocus();
        binding.setViewModel(loginVM);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = sharedPreferences.getBoolean(ISLOGIN, false);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener((view) -> {
            onSignUp(view);
        });

        password = findViewById(R.id.password);
        username = findViewById(R.id.username);

        if (isLogin) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onSuccess() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(LOGIN_USERNAME,loginVM.username).commit();
        sharedPreferences.edit().putInt(LOGIN_USERID,loginVM.userId).commit();
        sharedPreferences.edit().putBoolean(ISLOGIN, true).commit();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onFailure(String message) {
        if(password.getEditText().getText().toString().isEmpty() && !username.getEditText().getText().toString().isEmpty()) {
            password.setError(message);
            username.setError(null);
        }
        else if(username.getEditText().getText().toString().isEmpty() && !password.getEditText().getText().toString().isEmpty()) {
            username.setError(message);
            password.setError(null);
        }
        else
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError() {
        NetworkReceiverCallback.showSnackbar(findViewById(R.id.login_submit));
    }

    public void onSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (backCounts == 0) {
            Toast.makeText(this, "If you want to exit, Press back again!", Toast.LENGTH_SHORT).show();
            backCounts++;
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }
}