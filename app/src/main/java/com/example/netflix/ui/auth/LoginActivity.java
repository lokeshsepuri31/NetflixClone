package com.example.netflix.ui.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.util.GoogleClient;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String ISLOGIN = "IsLogin";
    public static final String LOGIN_USERNAME = "loggedInUsername";
    public static final String LOGIN_USERID = "loggedInUserId";
    LoginVM loginVM;

    int backCounts = 0;

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

        signup.setOnClickListener((view) -> {
            onSignUp(view);
        });
        setLauncher();

        password = findViewById(R.id.password);
        username = findViewById(R.id.username);

        if (isLogin) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    private void setLauncher(){
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == 0){
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try{
//                        GoogleSignInAccount
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void onSignIn(View view){
        GoogleSignInClient gsc = GoogleClient.getClient(this);
        Intent signInIntent = gsc.getSignInIntent();
        launcher.launch(signInIntent);
    }
}