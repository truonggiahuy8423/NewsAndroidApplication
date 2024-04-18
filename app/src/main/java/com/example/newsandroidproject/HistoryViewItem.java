package com.example.newsandroidproject;

import android.media.Image;

public class HistoryViewItem {
    int thumbnailImage;
    int commentImage;
    int bookmarkImage;
    String tittle;
    String source;
    String commentCount;

    public HistoryViewItem(int thumbnailImage, int commentImage, int bookmarkImage, String tittle, String source, String commentCount) {
        this.thumbnailImage = thumbnailImage;
        this.commentImage = commentImage;
        this.bookmarkImage = bookmarkImage;
        this.tittle = tittle;
        this.source = source;
        this.commentCount = commentCount;
    }

    public int getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(int thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(int commentImage) {
        this.commentImage = commentImage;
    }

    public int getBookmarkImage() {
        return bookmarkImage;
    }

    public void setBookmarkImage(int bookmarkImage) {
        this.bookmarkImage = bookmarkImage;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
