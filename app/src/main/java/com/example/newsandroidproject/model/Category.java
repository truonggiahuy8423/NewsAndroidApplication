package com.example.newsandroidproject.model;

import java.util.List;


public class Category {

    private Long categoryId;
    private String name;

//    private List<ArticleCategory> articleCategories;
    // Relation "Many"

    // Constructors, getters, and setters


    public Category() {
    }

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<ArticleCategory> getArticleCategories() {
//        return articleCategories;
//    }
//
//    public void setArticleCategories(List<ArticleCategory> articleCategories) {
//        this.articleCategories = articleCategories;
//    }
}
