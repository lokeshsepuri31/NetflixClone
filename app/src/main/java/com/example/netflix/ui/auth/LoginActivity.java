package com.example.netflix.ui.auth;


import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.util.GoogleClient;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;

import java.time.Duration;
import java.time.Instant;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String ISLOGIN = "IsLogin";
    public static final String LOGIN_USERNAME = "loggedInUsername";
    public static final String LOGIN_USERID = "loggedInUserId";
    LoginVM loginVM;
    Instant startTime;
    TextView signup;

    TextInputLayout username,password;

    ActivityResultLauncher<Intent> launcher;

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

        signup.setOnClickListener(this::onSignUp);
        password = binding.password;
        username = binding.username;

        if(password.getEditText() != null)
            password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!password.getEditText().getText().toString().isEmpty()){
                    password.setError(null);
                }else
                    password.setError("Please provide the Password");
            }
        });

        if(username.getEditText() != null)
            username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!username.getEditText().getText().toString().isEmpty()){
                    username.setError(null);
                }else
                    username.setError("Please provide the Username");
            }
        });


        if (isLogin) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }



    @Override
    public void onSuccess() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(LOGIN_USERNAME,loginVM.username).apply();
        sharedPreferences.edit().putInt(LOGIN_USERID,loginVM.userId).apply();
        sharedPreferences.edit().putBoolean(ISLOGIN, true).apply();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onFailure(String message) {

        if(message.equals("both")){
            username.setError("Please provide the Username");
            password.setError("Please provide the Password");
        }else if (message.contains("Password") && !message.contains("Username"))
            password.setError(message);
        else if(message.contains("Username") && !message.contains("Password"))
            username.setError(message);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant endTime = Instant.now();
            if(startTime == null){
                startTime = Instant.now();
                Toast.makeText(this, "If you want to exit back press again!", Toast.LENGTH_SHORT).show();
            }else {
                Duration duration = Duration.between(startTime, endTime);
                if(duration.toMillis() < 10000){
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }else {
                    startTime = endTime;
                    Toast.makeText(this, "If you want to exit back press again!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onSignIn(View view){
        GoogleSignInClient gsc = GoogleClient.getClient(this);
        Intent signInIntent = gsc.getSignInIntent();
        launcher.launch(signInIntent);
    }
}