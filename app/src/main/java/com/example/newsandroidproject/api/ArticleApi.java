package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleApi {
    @GET("/api/article/get-articles-in-news-feed")
    Call<List<ArticleInNewsFeedModel>> getArticlesInNewsFeed(@Query("page_index") int pageIndex);

    @GET("/api/article/get-article-by-id")
    Call<ArticleInReadingPageDTO> getArticleById(@Query("article_id") Long articleId);
}
