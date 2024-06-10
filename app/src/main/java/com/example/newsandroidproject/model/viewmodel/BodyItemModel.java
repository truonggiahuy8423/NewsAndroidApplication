package com.example.newsandroidproject.model.viewmodel;

public class BodyItemModel {
    private Long bodyItemId;
    private String imageName;
    private String content;
    private String dataImage;
    private String bodyTitle;
    private Integer ordinalNumber;

    public BodyItemModel(Long bodyItemId, String imageName, String content, String dataImage, String bodyTitle, Integer ordinalNumber) {
        this.bodyItemId = bodyItemId;
        this.imageName = imageName;
        this.content = content;
        this.dataImage = dataImage;
        this.bodyTitle = bodyTitle;
        this.ordinalNumber = ordinalNumber;
    }

    public Long getBodyItemId() {
        return bodyItemId;
    }

    public void setBodyItemId(Long bodyItemId) {
        this.bodyItemId = bodyItemId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getBodyTitle() {
        return bodyTitle;
    }

    public void setBodyTitle(String bodyTitle) {
        this.bodyTitle = bodyTitle;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}
