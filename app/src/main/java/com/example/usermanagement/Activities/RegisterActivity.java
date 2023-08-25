package com.example.usermanagement.Activities;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.RegisterResponse;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView loginLink;
    EditText name,email,password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        //hide action bar
        getSupportActionBar().hide();
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name=findViewById(R.id.etName);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);

        register=findViewById(R.id.registerBtn);
        loginLink=findViewById(R.id.loginLink);

        register.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.registerBtn) {
           registerUser();
        } else if (id == R.id.loginLink) {
            switchOnLogin();
        }
    }

    private void registerUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userName.isEmpty()){
            name.requestFocus();
            name.setError("Please enter your name");
            return;
        }
        if (userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter your email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please enter correct email");
            return;
        }
        if (userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter your password");
            return;
        }
        if (userPassword.length()<8){
            password.requestFocus();
            password.setError("Your password must contain 8 characters");
            return;
        }

        Call<RegisterResponse> registerResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .register(userName,userEmail,userPassword);

        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void switchOnLogin() {

        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
}