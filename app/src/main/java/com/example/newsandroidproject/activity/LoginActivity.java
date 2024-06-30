package com.example.newsandroidproject.activity;
import static com.example.newsandroidproject.common.JsonParser.parseError;

import android.animation.ObjectAnimator;
import static java.lang.System.exit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private TextView btnSignUpLogin;
    private ImageView iconPassHide;
    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btnSignIn);
        emailField = findViewById(R.id.edtGmailSignIn);
        passwordField = findViewById(R.id.edtPassWord);
        ImageView logo = findViewById(R.id.logo);

        // Tạo hiệu ứng fade-in
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        fadeIn.setDuration(2000); // Đặt thời gian hiệu ứng là 2 giây
        fadeIn.start();
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
        iconPassHide = findViewById(R.id.iconPassHide);

        // Event handler
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput(emailField.getText().toString(), passwordField.getText().toString());


            }
        });
        btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        iconPassHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconPassHide.setImageResource(R.drawable.ic_visible);
                } else {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iconPassHide.setImageResource(R.drawable.ic_invisible);
                }
                // Move the cursor to the end of the text
                passwordField.setSelection(passwordField.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }
    private void validateInput(String email, String password) {

        if(password.length() < 8 || password.length() > 20){
            Toast.makeText(LoginActivity.this, "Mật khẩu phải từ 8-20 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthenticationRequest request = new AuthenticationRequest(email, password);
        AuthenticationApi apiService = RetrofitService.getClient(this).create(AuthenticationApi.class);
        Call<Integer> call = apiService.validate(request);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    login(emailField.getText().toString(), passwordField.getText().toString());
                }
                else if(response.body() == 2){
                    Toast.makeText(LoginActivity.this, "Email sai/ không tồn tại", Toast.LENGTH_SHORT).show();

                }
                else if(response.body() == 3){
                    Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                System.out.println(throwable.getMessage());
            }
        });
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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
