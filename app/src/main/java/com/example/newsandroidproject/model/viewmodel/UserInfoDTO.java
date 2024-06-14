package com.example.newsandroidproject.model.viewmodel;

public class UserInfoDTO {
    private Long userId;
    private String name;
    private Integer role;
    private Long postCount;
    private Long followingCount;
    private Long followedCount;
    private boolean isFollowedByLoginUser;
    private String avatar;

    public UserInfoDTO(Long userId, String name, Integer role, Long postCount, Long followingCount, Long followedCount, boolean isFollowedByLoginUser, String avatar) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.followedCount = followedCount;
        this.isFollowedByLoginUser = isFollowedByLoginUser;
        this.avatar = avatar;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

    public Long getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(Long followedCount) {
        this.followedCount = followedCount;
    }

    public boolean getIsFollowedByLoginUser() {
        return isFollowedByLoginUser;
    }

    public void setFollowedByLoginUser(boolean followedByLoginUser) {
        isFollowedByLoginUser = followedByLoginUser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
