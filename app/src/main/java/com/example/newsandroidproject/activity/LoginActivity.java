package com.example.newsandroidproject.activity;
import static com.example.newsandroidproject.common.JsonParser.parseError;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.AuthenticationApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.AuthenticationRequest;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private Button loginBtn;
    private EditText emailField;
    private EditText passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btnSignIn);
        emailField = findViewById(R.id.edtGmailSignIn);
        passwordField = findViewById(R.id.edtPassWord);

        // Event handler
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
                login(emailField.getText().toString(), passwordField.getText().toString());
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }
    private void validateInput() {

    }

    private void login(String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest(email, password);
        AuthenticationApi apiService = RetrofitService.getClient(this).create(AuthenticationApi.class);
        Call<AuthenticationResponse> call = apiService.login(request);

        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    AuthenticationResponse authResponse = response.body();
                    saveToken(authResponse.getToken());
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    // Chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, PostArticleActivity.class);
                    startActivity(intent);

                    // Kết thúc LoginActivity để không quay lại màn hình đăng nhập
                    finish();
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("LoginActivity", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
