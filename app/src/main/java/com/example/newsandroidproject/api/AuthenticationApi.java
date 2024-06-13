package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.dto.AuthenticationRequest;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    @POST("/api/auth/login")
    Call<AuthenticationResponse> login(@Body AuthenticationRequest body);

    @POST("/api/auth/register")
    Call<AuthenticationResponse> register(@Body AuthenticationRequest body);
}
