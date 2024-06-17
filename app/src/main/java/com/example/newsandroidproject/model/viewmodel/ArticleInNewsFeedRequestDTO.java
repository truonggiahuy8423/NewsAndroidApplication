package com.example.newsandroidproject.model.viewmodel;

import java.io.Serializable;

public class ArticleInNewsFeedRequestDTO implements Serializable {
    public boolean isLoading = false;
    // article
    private Long articleId;
    private String title;
    private String description;
    private String thumbnail;
    private String thumbnailName;
    private String createTime;
    private String modifyTime;
    private Long viewCount;
    private Long commentCount;
    // author
    private Long userId;
    private String userName;
    private String avatar;
    private Long followCount;
    private Long saveCount;

    public Long getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Long saveCount) {
        this.saveCount = saveCount;
    }
    // Construction
    public ArticleInNewsFeedRequestDTO() {
    }

    public ArticleInNewsFeedRequestDTO(Long articleId, String title, String description, String thumbnail, String thumbnailName, String createTime, String modifyTime, Long viewCount, Long commentCount, Long userId, String userName, String avatar, Long followCount, Long saveCount) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailName = thumbnailName;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.followCount = followCount;
        this.saveCount = saveCount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }



    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    @Override
    public String toString() {
        return "ArticleInNewsFeedModel{" + '\n' +
                "articleId=" + articleId + '\n' +
                ", title=" + title + '\n' +
                ", description=" + description + '\n' +
                ", thumbnail=" + thumbnail + '\n' +
                ", thumbnailName=" + thumbnailName + '\n' +
                ", createTime=" + createTime + '\n' +
                ", modifyTime=" + modifyTime + '\n' +
                ", viewCount=" + viewCount + '\n' +
                ", commentCount=" + commentCount + '\n' +
                ", userId=" + userId + '\n' +
                ", userName=" + userName + '\n' +
                ", avatar=" + avatar + '\n' +
                ", followCount=" + followCount + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return (this.articleId.equals(((ArticleInNewsFeedRequestDTO)o).articleId));
    }

    @Override
    public int hashCode() {
        return articleId.hashCode();
    }
}
