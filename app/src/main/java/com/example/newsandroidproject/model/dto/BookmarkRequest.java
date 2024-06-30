package com.example.newsandroidproject.model.dto;


public class BookmarkRequest {
    private Long userId;
    private Long articleId;
    private String time;

    public BookmarkRequest(Long userId, Long articleId, String time) {
        this.userId = userId;
        this.articleId = articleId;
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
