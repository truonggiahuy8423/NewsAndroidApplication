package com.example.newsandroidproject.model.dto;

public class PostArticleResponse {
    public int rvPosition;
    public Long articleId;

    public PostArticleResponse(Long articleId, int rvPosition) {
        this.articleId = articleId;
        this.rvPosition = rvPosition;
    }
}
