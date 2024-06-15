package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.Follow;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FollowApi {
    @POST("/follow-request")
    Call<Follow> createFollow(@Body Follow follow);

    @DELETE("/unfollow-request")
    Call<Void> deleteFollow(@Query("followedId") Long followedId, @Query("followerId") Long followerId);
}
