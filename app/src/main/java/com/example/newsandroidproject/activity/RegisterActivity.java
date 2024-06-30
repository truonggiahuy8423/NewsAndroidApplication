package com.example.newsandroidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.AuthenticationApi;
import com.example.newsandroidproject.databinding.ActivityRegisterBinding;
import com.example.newsandroidproject.model.dto.AuthenticationRequest;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;
import com.example.newsandroidproject.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    // register activity binding
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnSignUp.setOnClickListener(v -> {
            // validate input
            validateInput();
            // register
            Log.i("RegisterActivity", "Register button clicked");
            register();
        });

    }

    private void register() {
        String email = binding.edtGmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        AuthenticationApi apiService = RetrofitService.getClient(this).create(AuthenticationApi.class);
        Call<AuthenticationResponse> call = apiService.register(request);

        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, retrofit2.Response<AuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("RegisterActivity", "Register success");
                    // go to login activity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("RegisterActivity", "Register failed");
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e("RegisterActivity", "Register failed", t);
                Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void validateInput() {
        // check if email is empty
        if (binding.edtGmail.getText().toString().isEmpty()) {
            binding.edtGmail.setError("Email is required");
            binding.edtGmail.requestFocus();
            return;
        }
        // check if password is empty
        if (binding.edtPassword.getText().toString().isEmpty()) {
            binding.edtPassword.setError("Password is required");
            binding.edtPassword.requestFocus();
            return;
        }
        // check if confirm password is empty
        if (binding.edtConfirmPassword.getText().toString().isEmpty()) {
            binding.edtConfirmPassword.setError("Confirm password is required");
            binding.edtConfirmPassword.requestFocus();
            return;
        }
        // check if password and confirm password are the same
        if (!binding.edtPassword.getText().toString().equals(binding.edtConfirmPassword.getText().toString())) {
            binding.edtConfirmPassword.setError("Password and confirm password must be the same");
            binding.edtConfirmPassword.requestFocus();
        }
    }
}
