package com.example.newsandroidproject.model;

import java.util.Date;

public class Follow {
    private Long followedId;
    private Long followerId;
    private String time;

    public Follow(Long followedId, Long followerId, String time) {
        this.followedId = followedId;
        this.followerId = followerId;
        this.time = time;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
