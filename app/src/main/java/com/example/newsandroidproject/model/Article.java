package com.example.newsandroidproject.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Article {
    private Long articleId;
    private Date createTime;
    private Date modifyTime;
    private String description;
    private String title;
    private String thumbnail;
    private String thumbnailName;

    // Relation "Many"

    private User user;


    // Constructors, getters, and setters

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailName='" + thumbnailName + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return (this.articleId.equals(((Article)o).articleId));
    }

    @Override
    public int hashCode() {
        return articleId.hashCode();
    }
}

