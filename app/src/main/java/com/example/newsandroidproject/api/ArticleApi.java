package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleApi {
    @GET("/api/article/get-articles-in-news-feed")
    Call<List<ArticleInNewsFeedModel>> getArticlesInNewsFeed();
}
