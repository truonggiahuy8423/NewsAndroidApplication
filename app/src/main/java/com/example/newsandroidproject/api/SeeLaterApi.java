package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface SeeLaterApi {

    @GET("/api/see-later/articles")
    Call<List<ArticleInNewsFeedModel>> getArticles();
}
