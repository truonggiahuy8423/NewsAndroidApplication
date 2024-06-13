package com.example.newsandroidproject.model.dto;

public class NotificationDTO {

    private Long notificationId;
    private String content;
    private Long userId;
    private Long notificationTypeId;
    private Long articleId;
    private Long commentId;

    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationId, String content, Long userId, Long notificationTypeId, Long articleId, Long commentId) {
        this.notificationId = notificationId;
        this.content = content;
        this.userId = userId;
        this.notificationTypeId = notificationTypeId;
        this.articleId = articleId;
        this.commentId = commentId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(Long notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
