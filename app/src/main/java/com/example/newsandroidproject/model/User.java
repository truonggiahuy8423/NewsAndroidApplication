package com.example.newsandroidproject.model;

import java.util.Date;
import java.util.List;

public class User {

    private Long userId;
    private Date registerDate;
    private String name;
    private String phone;
    private String email;
    private Date birthDate;
    private Boolean gender;
    private String avatar;
    private String password;
    private Boolean isAuthor;

    // Relation "Many"

    // Constructors, getters, and setters



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthor() {
        return isAuthor;
    }

    public void setAuthor(Boolean author) {
        isAuthor = author;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", registerDate=" + registerDate +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", isAuthor=" + isAuthor +
                '}';
    }
}
