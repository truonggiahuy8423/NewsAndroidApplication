package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.FavoriteViewItemModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteArticleApi {

    @GET("/api/favorite/articles")
    Call<List<ArticleInNewsFeedModel>> getArticles();
}
