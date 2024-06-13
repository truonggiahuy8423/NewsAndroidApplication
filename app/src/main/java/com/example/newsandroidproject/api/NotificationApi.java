package com.example.newsandroidproject.api;



import com.example.newsandroidproject.model.dto.NotificationDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationApi {

    @GET("/api/notification/{userId}")
    Call<List<NotificationDTO>> getNotification(@Path("userId") Long userId);
}
