package com.example.newsandroidproject.model.dto;

public class NotificationDTO {

    private Long notificationId;
    private String content;
    private Long userId;
    private Long notificationTypeId;

    private Long articleId;
    private String thumbnail;

    private Integer type;

    private Long commentId;


    private String actorName;
    private String actorAva;


    private Integer isSeen;


    public Integer getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Integer isSeen) {
        this.isSeen = isSeen;
    }

    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationId, String content, Long userId, Long notificationTypeId, Long articleId, String thumbnail, Integer type, Long commentId, String actorName, String actorAva, Integer isSeen) {
        this.notificationId = notificationId;
        this.content = content;
        this.userId = userId;
        this.notificationTypeId = notificationTypeId;
        this.articleId = articleId;
        this.thumbnail = thumbnail;
        this.type = type;
        this.commentId = commentId;
        this.actorName = actorName;
        this.actorAva = actorAva;
        this.isSeen = isSeen;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorAva() {
        return actorAva;
    }

    public void setActorAva(String actorAva) {
        this.actorAva = actorAva;
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
