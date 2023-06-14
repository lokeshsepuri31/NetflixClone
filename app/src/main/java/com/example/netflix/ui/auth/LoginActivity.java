package com.example.netflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.util.NetworkReceiver;
import com.example.netflix.util.PicassoVM;
import com.example.netflix.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String ISLOGIN = "IsLogin";
    LoginVM loginVM;

    TextView signup;

    TextInputLayout password;

    NetworkReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Intent errorIntent = new Intent(this,ErrorActivity.class);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeReceiver = new NetworkReceiver(this);
        registerReceiver(networkChangeReceiver, intentFilter);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginVM = new ViewModelProvider(this).get(LoginVM.class);
        loginVM.loginListener = this;
        binding.setViewModel(loginVM);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = sharedPreferences.getBoolean(ISLOGIN, false);
        ImageView imageView = findViewById(R.id.imageView);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener((view)->{
            onSignUp(view);
        });

        password = findViewById(R.id.password);

        String url = getResources().getString(R.string.login_image);
        PicassoVM picassoVM = new ViewModelProvider(this).get(PicassoVM.class);
        picassoVM.setLoginImage(imageView,url);

        if (isLogin) {
//            sharedPreferences.edit().clear().commit();
            startActivity(new Intent(this, HomeActivity.class));
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onSuccess() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(ISLOGIN, true).commit();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onFailure(String message) {
        password.setError(message);
    }

    public void onSignUp(View view){
        startActivity(new Intent(this,SignUpActivity.class));
    }
}