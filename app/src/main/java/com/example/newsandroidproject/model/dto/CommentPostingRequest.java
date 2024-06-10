package com.example.newsandroidproject.model.dto;

public class CommentPostingRequest {
    private String content;

    private String createTime;

    private String modifyTime;

    private Long articleId;

    private Long parentCommentId;

    private Long userId;

    public CommentPostingRequest() {
    }

    public CommentPostingRequest(String content, String createTime, String modifyTime, Long articleId, Long parentCommentId, Long userId) {
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.articleId = articleId;
        this.parentCommentId = parentCommentId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
