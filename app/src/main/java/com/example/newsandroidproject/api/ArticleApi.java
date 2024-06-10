package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.dto.CommentPostingRequest;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.model.viewmodel.ArticleScrollPageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleApi {
    @GET("/api/article/get-articles-in-news-feed")
    Call<List<ArticleInNewsFeedModel>> getArticlesInNewsFeed(@Query("page_index") int pageIndex);

    @GET("/api/article/get-article-by-id")
    Call<ArticleInReadingPageDTO> getArticleById(@Query("article_id") Long articleId);

    @GET("/api/article/get-comments-by-article-id")
    Call<List<UserCommentDTO>> getCommentsByArticleId(@Query("article_id") Long articleId, @Query("page_index") int pageIndex);

    @POST("api/article/post-comment")
    Call<UserCommentDTO> postComment(@Body CommentPostingRequest commentPostingRequest);

    @GET("/get-articles-in-scroll-page")
    Call<List<ArticleScrollPageModel>> getArticlesInScrollPage(@Query("page_index") int pageIndex);
}
