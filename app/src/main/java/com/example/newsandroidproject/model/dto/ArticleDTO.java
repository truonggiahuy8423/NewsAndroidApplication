package com.example.newsandroidproject.model.dto;

import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.Category;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleDTO implements Serializable {
    private Long articleId;
    private Date createTime;
    private Date modifyTime;
    private String description;
    private String title;
    private String thumbnail;
    private String thumbnailName;

    UniqueList<Category> categories;

    List<BodyItem> items;

    public ArticleDTO() {
    }

    public ArticleDTO(Long articleId, Date createTime, Date modifyTime, String description, String title, String thumbnail, String thumbnailName, UniqueList<Category> categories, List<BodyItem> items) {
        this.articleId = articleId;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.description = description;
        this.title = title;
        this.thumbnail = thumbnail;
        this.thumbnailName = thumbnailName;
        this.categories = categories;
        this.items = items;
    }

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

    public UniqueList<Category> getCategories() {
        return categories;
    }

    public void setCategories(UniqueList<Category> categories) {
        this.categories = categories;
    }

    public List<BodyItem> getItems() {
        return items;
    }

    public void setItems(List<BodyItem> items) {
        this.items = items;
    }
}
