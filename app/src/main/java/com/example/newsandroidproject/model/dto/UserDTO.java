package com.example.newsandroidproject.model.dto;

public class UserDTO {
    private Long userId;
    private String name;
    private String avatar;
    private Long followerCount;

    public UserDTO() {
    }

    public UserDTO(Long userId, String name, String avatar, Long followerCount) {
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.followerCount = followerCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getFolloweCount() {
        return followerCount;
    }

    public void setFolloweCount(Long followerCount) {
        this.followerCount = followerCount;
    }
}
