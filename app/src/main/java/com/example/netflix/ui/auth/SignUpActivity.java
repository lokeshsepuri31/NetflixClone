package com.example.netflix.ui.auth;

import static com.example.netflix.ui.auth.LoginActivity.LOGIN_USERNAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.databinding.ActivitySignUpBinding;
import com.example.netflix.util.NetworkReceiverCallback;
import com.example.netflix.util.TextValidationUtility;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements LoginListener{

    EditText email,password,repassword;

    TextInputLayout emailLayout,passwordLayout,rePasswordLayout;
    SignUpVM signUpVM;

    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
         signUpVM = new ViewModelProvider(this).get(SignUpVM.class);
        signUpVM.loginListener = this;
        signUpVM.databaseHandler = DatabaseHandler.getInstance(this);
        binding.setViewModel(signUpVM);
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);
        rePasswordLayout = findViewById(R.id.repassword);
        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener((view)->{
            onBackPressed();
        });

        password = passwordLayout.getEditText();
        email = emailLayout.getEditText();
        repassword = rePasswordLayout.getEditText();
        email.requestFocus();
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextValidationUtility.emailValidation(email.getText().toString())) {
                    emailLayout.setError(null);
                } else {
                    emailLayout.setError("Please provide a valid email");
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextValidationUtility.passwordSpecialCharacterValidation(password.getText().toString())){
                    if(TextValidationUtility.passwordLengthValidation(password.getText().toString())){
                        passwordLayout.setError(null);
                    }else{
                        passwordLayout.setError("Length of password should be between 8 and 12 characters.");
                    }
                }else{
                    passwordLayout.setError("Password should contain atleast one special character.");
                }

            }
        });

        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(TextValidationUtility.isBothPasswordsEqual(password.getText().toString(),repassword.getText().toString())){
                    rePasswordLayout.setError(null);
                }else{
                    rePasswordLayout.setError("Password and Confirm Password are not same");
                }
            }
        });


        getSupportActionBar().hide();
    }

    @Override
    public void onSuccess() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String[] username = signUpVM.email.split("@");
        sharedPreferences.edit().putString(LOGIN_USERNAME,username[0]).commit();

        Intent intent = new Intent(this,SuccessActivity.class);
        intent.putExtra("message","Your Account is ready!, Login with username (Ex: something@gmail.com your username is 'something'.)");
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkError() {
        NetworkReceiverCallback.showSnackbar(findViewById(R.id.signup_submit));
    }
}