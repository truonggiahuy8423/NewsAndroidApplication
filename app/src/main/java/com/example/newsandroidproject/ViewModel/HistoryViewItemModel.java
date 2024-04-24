package com.example.newsandroidproject.ViewModel;

public class HistoryViewItemModel {
    int thumbnailImage;
    String tittle;
    String content;

    public HistoryViewItemModel(int thumbnailImage, String tittle, String content) {
        this.thumbnailImage = thumbnailImage;
        this.tittle = tittle;
        this.content = content;
    }

    public int getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(int thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
