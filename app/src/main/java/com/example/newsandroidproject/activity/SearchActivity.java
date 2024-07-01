package com.example.newsandroidproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.SearchResultAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText searchInput;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    RecyclerView searchResult;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = findViewById(R.id.search_input);
        searchResult = findViewById(R.id.search_results);
        searchButton = findViewById(R.id.search_button);
        searchResult.setHasFixedSize(true);
        searchResult.setItemViewCacheSize(20);

        searchInput.requestFocus();
        searchButton.setOnClickListener(v -> search());
    }


    // search method
    private void search() {
        String searchQuery = searchInput.getText().toString();
        // call api
        executorService.execute(() -> {
            // endpoint is /    @GetMapping("/api/article/search")
            ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
            Call<List<ArticleInNewsFeedModel>> call = apiService.search(searchQuery);
            call.enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
                @Override
                public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                    if (response.isSuccessful()) {
                        List<ArticleInNewsFeedModel> articles = response.body();
                        runOnUiThread(() -> {
                            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(articles);
                            searchResult.setAdapter(searchResultAdapter);
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<ArticleInNewsFeedModel>> call, Throwable t) {
                    Log.i("SearchActivity", "Search failed", t);
                }
            });

        });
    }


}