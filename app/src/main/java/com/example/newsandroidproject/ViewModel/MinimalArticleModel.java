package com.example.newsandroidproject.ViewModel;

import android.graphics.Bitmap;

import java.util.Date;

public class MinimalArticleModel {
    String title, description, author_name;
    Integer view_count, comment_count, subscribe_count;
    Bitmap thumbnail, author_image;
    Date post_time;

    /// Các function khác


    public MinimalArticleModel() {
    }

    public MinimalArticleModel(String title, String description, String author_name, Integer view_count, Integer comment_count, Integer subscribe_count, Bitmap thumbnail, Bitmap author_image, Date post_time) {
        this.title = title;
        this.description = description;
        this.author_name = author_name;
        this.view_count = view_count;
        this.comment_count = comment_count;
        this.subscribe_count = subscribe_count;
        this.thumbnail = thumbnail;
        this.author_image = author_image;
        this.post_time = post_time;
    }

    @Override
    public String toString() {
        return "MinimalArticleModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author_name='" + author_name + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public void setcomment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public void setSubscribe_count(Integer subscribe_count) {
        this.subscribe_count = subscribe_count;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setAuthor_image(Bitmap author_image) {
        this.author_image = author_image;
    }

    public void setPost_time(Date post_time) {
        this.post_time = post_time;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Integer getView_count() {
        return view_count;
    }

    public Integer getcomment_count() {
        return comment_count;
    }

    public Integer getSubscribe_count() {
        return subscribe_count;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public Bitmap getAuthor_image() {
        return author_image;
    }

    public Date getPost_time() {
        return post_time;
    }
}
