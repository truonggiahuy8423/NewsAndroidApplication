package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.ArticleScrollPageModel;
import com.example.newsandroidproject.model.viewmodel.UserInfoDTO;
import com.example.newsandroidproject.model.viewmodel.UserNavigationMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/get-user-info")
    Call<UserInfoDTO> getUserInfo(@Query("userId") Long userId);

    @GET("/set-up-user-menu")
    Call<UserNavigationMenu> getUserNavigationMenu();
}
