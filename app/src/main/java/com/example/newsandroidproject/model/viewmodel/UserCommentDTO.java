package com.example.newsandroidproject.model.viewmodel;

import com.example.newsandroidproject.model.Article;

import java.util.Date;
import java.util.List;

public class UserCommentDTO {
    private Long commentId;

    private String content;

    private Date createTime;

    private Date modifyTime;

    // Relation "One"
    private Long likeCommentCount;

    private Long articleId;

    private Long parentCommentId;

    private Long userId;
    private String name;
    private String avatar;

    public UserCommentDTO() {
    }

    public UserCommentDTO(Long commentId, String content, Date createTime, Date modifyTime, Long likeCommentCount, Long articleId, Long parentCommentId, Long userId, String name, String avatar) {
        this.commentId = commentId;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.likeCommentCount = likeCommentCount;
        this.articleId = articleId;
        this.parentCommentId = parentCommentId;
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getLikeCommentCount() {
        return likeCommentCount;
    }

    public void setLikeCommentCount(Long likeCommentCount) {
        this.likeCommentCount = likeCommentCount;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return (this.commentId.equals(((UserCommentDTO)o).commentId));
    }

    @Override
    public int hashCode() {
        return commentId.hashCode();
    }
}
