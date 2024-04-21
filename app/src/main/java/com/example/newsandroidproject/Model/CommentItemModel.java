package com.example.newsandroidproject.Model;

public class CommentItemModel {
    private Integer cmtAvatar, cmtNoLiked;
    private String cmtUsrName, cmtContent, cmtTime;

    public CommentItemModel(Integer cmtAvatar, String cmtUsrName, String cmtContent, String cmtTime, Integer cmtNoLiked) {
        this.cmtAvatar = cmtAvatar;
        this.cmtUsrName = cmtUsrName;
        this.cmtContent = cmtContent;
        this.cmtTime = cmtTime;
        this.cmtNoLiked = cmtNoLiked;
    }

    public Integer getCmtAvatar() {
        return cmtAvatar;
    }

    public void setCmtAvatar(Integer cmtAvatar) {
        this.cmtAvatar = cmtAvatar;
    }

    public String getCmtUsrName() {
        return cmtUsrName;
    }

    public void setCmtUsrName(String cmtUsrName) {
        this.cmtUsrName = cmtUsrName;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public String getCmtTime() {
        return cmtTime;
    }

    public void setCmtTime(String cmtTime) {
        this.cmtTime = cmtTime;
    }

    public Integer getCmtNoLiked() {
        return cmtNoLiked;
    }

    public void setCmtNoLiked(Integer cmtNoLiked) {
        this.cmtNoLiked = cmtNoLiked;
    }
}
