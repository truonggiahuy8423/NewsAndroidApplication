package com.example.newsandroidproject.viewmodel;

public class NewsContentModel {
    private String title_0, title_1, content_0, content_1, txtImgContent;
    private Integer imgNews;

    public NewsContentModel(String title_0, String title_1, String content_0, int imgNews, String txtImgContent, String content_1) {
        this.title_0 = title_0;
        this.title_1 = title_1;
        this.content_0 = content_0;
        this.imgNews = imgNews;
        this.txtImgContent = txtImgContent;
        this.content_1 = content_1;
    }

    public NewsContentModel(String title_0, String title_1, Integer imgNews) {
        this.title_0 = title_0;
        this.title_1 = title_1;
        this.imgNews = imgNews;
    }

    public String getTitle_0() {
        return title_0;
    }

    public void setTitle_0(String title_0) {
        this.title_0 = title_0;
    }

    public String getTitle_1() {
        return title_1;
    }

    public void setTitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getContent_0() {
        return content_0;
    }

    public void setContent_0(String content_0) {
        this.content_0 = content_0;
    }

    public String getContent_1() {
        return content_1;
    }

    public void setContent_1(String content_1) {
        this.content_1 = content_1;
    }

    public Integer getImgNews() {
        return imgNews;
    }

    public void setImgNews(int imgNews) {
        this.imgNews = imgNews;
    }

    public String getTxtImgContent() {
        return txtImgContent;
    }

    public void setTxtImgContent(String txtImgContent) {
        this.txtImgContent = txtImgContent;
    }
}
