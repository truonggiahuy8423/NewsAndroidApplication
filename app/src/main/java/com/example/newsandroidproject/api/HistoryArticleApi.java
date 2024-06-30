package com.example.newsandroidproject.api;

import com.example.newsandroidproject.model.viewmodel.HistoryViewItemModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public interface HistoryArticleApi {

    @POST("/api/history-articles/by-ids")
    Call<List<HistoryViewItemModel>> getArticleByIds(@Body ArrayList<Long> idList);
}
