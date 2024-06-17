package com.example.newsandroidproject.model;

import java.io.Serializable;
import java.util.List;


public class Category implements Serializable {

    private Long categoryId;
    private String name;

//    private List<ArticleCategory> articleCategories;
    // Relation "Many"

    // Constructors, getters, and setters

    @Override
    public String toString() {
        return "{" +
                "\"categoryId\":" + categoryId +
                ", \"name\":\"" + name + '\"' +
                '}';
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return (this.categoryId.equals(((Category)o).categoryId));
    }

    @Override
    public int hashCode() {
        return categoryId.hashCode();
    }
}
