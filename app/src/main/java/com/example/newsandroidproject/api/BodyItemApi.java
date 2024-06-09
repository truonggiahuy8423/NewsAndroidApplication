package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.BodyItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BodyItemApi {
    @GET("/test-get-body-item/{articleId}")
    Call<List<BodyItemModel>> getBodyItemByArticleId(@Path("articleId") Long articleId);
}
