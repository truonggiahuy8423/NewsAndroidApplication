package com.example.newsandroidproject.model.dto;

import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;

import java.util.List;


public class CommentLoadingResponse {
    private List<UserCommentDTO> comments;
    private Long commentCount;
    private Integer maxPageIndex;

    public CommentLoadingResponse(List<UserCommentDTO> comments, Long commentCount, Integer maxPageIndex) {
        this.comments = comments;
        this.commentCount = commentCount;
        this.maxPageIndex = maxPageIndex;
    }

    public CommentLoadingResponse() {
    }

    public List<UserCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<UserCommentDTO> comments) {
        this.comments = comments;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getMaxPageIndex() {
        return maxPageIndex;
    }

    public void setMaxPageIndex(Integer maxPageIndex) {
        this.maxPageIndex = maxPageIndex;
    }
}
