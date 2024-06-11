package com.example.newsandroidproject.model.dto;


public class LikeCommentDTO {

    private String time;

    private Long userId;

    private Long commentId;

    private Boolean isSuccess;

    public LikeCommentDTO() {
    }

    public LikeCommentDTO(String time, Long userId, Long commentId, Boolean isSuccess) {
        this.time = time;
        this.userId = userId;
        this.commentId = commentId;
        this.isSuccess = isSuccess;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
