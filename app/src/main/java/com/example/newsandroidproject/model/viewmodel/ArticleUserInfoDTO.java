package com.example.newsandroidproject.model.viewmodel;

import java.util.Date;

public class ArticleUserInfoDTO {
    private Long articleId;
    private String title;
    private Date createTime;
    private Date modifyTime;
    private Long viewCount;
    private Long userId;
    private String thumbnail;

    public ArticleUserInfoDTO(Long articleId, String title, Date createTime, Date modifyTime, Long viewCount, Long userId, String thumbnail) {
        this.articleId = articleId;
        this.title = title;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.viewCount = viewCount;
        this.userId = userId;
        this.thumbnail = thumbnail;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
