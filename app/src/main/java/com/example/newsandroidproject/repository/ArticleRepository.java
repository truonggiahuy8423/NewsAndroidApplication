package com.example.newsandroidproject.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.example.newsandroidproject.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.viewmodel.NewsContentModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private ArticleApi apiService;

    public ArticleRepository() {
        apiService = RetrofitService.getClient().create(ArticleApi.class);
    }
    public LiveData<List<ArticleInNewsFeedModel>> getArticlesInNewsFeed() {
        final MutableLiveData<List<ArticleInNewsFeedModel>> data = new MutableLiveData<>();
        apiService.getArticlesInNewsFeed().enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
            @Override
            public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.d("Test API", "Error: " + response.body());

                } else {
                    data.setValue(null);
                    Log.d("Test API", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ArticleInNewsFeedModel>> call, Throwable t) {
                data.setValue(null);
                Log.d("Test API", "Failure: " + t.getMessage());
            }
        });
        Log.d("Test", data.getValue() == null ? "null" : "not null");

        return data;
    }
}
