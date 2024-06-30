package com.example.newsandroidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.AuthenticationApi;
import com.example.newsandroidproject.model.dto.AuthenticationRequest;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;
import com.example.newsandroidproject.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtGmail, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private ImageView btnBack;
    private TextView btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialComponents();

        btnSignUp.setOnClickListener(v -> {
            // validate input
            validateInput();
            // register
            Log.i("RegisterActivity", "Register button clicked");
            register();
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialComponents(){
        edtGmail = findViewById(R.id.edt_gmail);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBackRegister);
        btnSignIn = findViewById(R.id.btn_sign_in);

    }
    private void register() {
        String email = edtGmail.getText().toString();
        String password = edtPassword.getText().toString();
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
        if (edtGmail.getText().toString().isEmpty()) {
            edtGmail.setError("Email is required");
            edtGmail.requestFocus();
            return;
        }
        // check if password is empty
        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }
        // check if confirm password is empty
        if (edtConfirmPassword.getText().toString().isEmpty()) {
            edtConfirmPassword.setError("Confirm password is required");
            edtConfirmPassword.requestFocus();
            return;
        }
        // check if password and confirm password are the same
        if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError("Password and confirm password must be the same");
            edtConfirmPassword.requestFocus();
        }
    }
}
