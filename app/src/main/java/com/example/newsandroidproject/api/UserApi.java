package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.User;
import com.example.newsandroidproject.model.dto.UserDTO;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/api/user/get-user-info")
    Call<User> getUserInfo();

    @GET("/api/user/get-user-info2")
    Call<UserDTO> getUserInfo2();

}
