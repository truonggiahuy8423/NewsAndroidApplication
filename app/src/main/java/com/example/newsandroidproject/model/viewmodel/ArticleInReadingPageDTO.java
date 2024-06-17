package com.example.newsandroidproject.model.viewmodel;

import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.Category;

import java.io.Serializable;
import java.util.List;

public class ArticleInReadingPageDTO extends ArticleInNewsFeedModel implements Serializable {
    private List<BodyItem> bodyItemList;
    private List<Category> categories;

    public List<BodyItem> getBodyItemList() {
        return bodyItemList;
    }

    // Hàm khởi tạo sử dụng ArticleInNewsFeedModel
    public ArticleInReadingPageDTO(ArticleInNewsFeedModel newsFeedArticleDTO, List<BodyItem> bodyItemList, List<Category> categories) {
        super(newsFeedArticleDTO.getArticleId(),
                newsFeedArticleDTO.getTitle(),
                newsFeedArticleDTO.getDescription(),
                newsFeedArticleDTO.getThumbnail(),
                newsFeedArticleDTO.getThumbnailName(),
                newsFeedArticleDTO.getCreateTime(),
                newsFeedArticleDTO.getModifyTime(),
                newsFeedArticleDTO.getViewCount(),
                newsFeedArticleDTO.getCommentCount(),
                newsFeedArticleDTO.getUserId(),
                newsFeedArticleDTO.getUserName(),
                newsFeedArticleDTO.getAvatar(),
                newsFeedArticleDTO.getFollowCount(),
                newsFeedArticleDTO.getSaveCount());
        this.bodyItemList = bodyItemList;
        this.categories = categories;
    }

    public void setBodyItemList(List<BodyItem> bodyItemList) {
        this.bodyItemList = bodyItemList;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ArticleInReadingPageDTO{" +
                "bodyItemList=" + bodyItemList +
                ", categories=" + categories +
                '}' + super.toString();
    }
}
