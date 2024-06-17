package com.example.newsandroidproject.model.viewmodel;

import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.Category;

import java.io.Serializable;
import java.util.List;

public class PostArticleRequestDTO implements Serializable {
    // Các thuộc tính của ArticleInNewsFeedRequestDTO
    public int rvPosition;
    private Long articleId;
    private String title;
    private String description;
    private String thumbnail;
    private String thumbnailName;
    private String createTime;
    private String modifyTime;
    private Long viewCount;
    private Long commentCount;
    private Long userId;
    private String userName;
    private String avatar;
    private Long followCount;
    private Long saveCount;

    // Các thuộc tính của PostArticleRequestDTO
    private List<BodyItem> bodyItemList;
    private List<Category> categories;

    // Getters và Setters cho các thuộc tính
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

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    public Long getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(Long saveCount) {
        this.saveCount = saveCount;
    }

    public List<BodyItem> getBodyItemList() {
        return bodyItemList;
    }

    public void setBodyItemList(List<BodyItem> bodyItemList) {
        this.bodyItemList = bodyItemList;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // Hàm khởi tạo sử dụng ArticleInNewsFeedModel
    public PostArticleRequestDTO(ArticleInNewsFeedRequestDTO newsFeedArticleDTO, List<BodyItem> bodyItemList, List<Category> categories) {
        this.articleId = newsFeedArticleDTO.getArticleId();
        this.title = newsFeedArticleDTO.getTitle();
        this.description = newsFeedArticleDTO.getDescription();
        this.thumbnail = newsFeedArticleDTO.getThumbnail();
        this.thumbnailName = newsFeedArticleDTO.getThumbnailName();
        this.createTime = newsFeedArticleDTO.getCreateTime();
        this.modifyTime = newsFeedArticleDTO.getModifyTime();
        this.viewCount = newsFeedArticleDTO.getViewCount();
        this.commentCount = newsFeedArticleDTO.getCommentCount();
        this.userId = newsFeedArticleDTO.getUserId();
        this.userName = newsFeedArticleDTO.getUserName();
        this.avatar = newsFeedArticleDTO.getAvatar();
        this.followCount = newsFeedArticleDTO.getFollowCount();
        this.saveCount = newsFeedArticleDTO.getSaveCount();
        this.bodyItemList = bodyItemList;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "{" +
                "\"articleId\":" + articleId +
                ", \"title\":\"" + title + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"thumbnail\":\"" + thumbnail + '\"' +
                ", \"thumbnailName\":\"" + thumbnailName + '\"' +
                ", \"createTime\":\"" + createTime + '\"' +
                ", \"modifyTime\":\"" + modifyTime + '\"' +
                ", \"viewCount\":" + viewCount +
                ", \"commentCount\":" + commentCount +
                ", \"userId\":" + userId +
                ", \"userName\":\"" + userName + '\"' +
                ", \"avatar\":\"" + "" + '\"' +
                ", \"followCount\":" + followCount +
                ", \"saveCount\":" + saveCount +
                ", \"bodyItemList\":" + bodyItemList +
                ", \"categories\":" + categories +
                '}';
    }

}
