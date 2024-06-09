package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import androidx.viewpager.widget.ViewPager;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.ViewPagerAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleScrollPageModel;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollModeFragment extends Fragment {
    private ArticleApi api;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<ArticleScrollPageModel> articleList;
    public ScrollModeFragment() {
        // Required empty public constructor
    }

    public static ScrollModeFragment newInstance() {
        ScrollModeFragment fragment = new ScrollModeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_scroll_mode, container, false);
        viewPager = root.findViewById(R.id.viewpager);
        setUpAdapter();
        loadDataFromApi();
        return root;
    }

    private void setUpAdapter() {
        articleList = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter((MainActivity) getActivity(),articleList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
    }

    private void loadDataFromApi() {
        articleList.clear();
        ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
        apiService.getArticlesInScrollPage().enqueue(new Callback<List<ArticleScrollPageModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<ArticleScrollPageModel>> call, Response<List<ArticleScrollPageModel>> response) {
                if (response.isSuccessful()) {
                    articleList.addAll(response.body());
                    Log.d("Test API", "Error: " + response.body());
                    viewPagerAdapter.notifyDataSetChanged();

                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ArticleScrollPageModel>> call, Throwable throwable) {
                Log.d("Test API", "Failure: " + throwable.getMessage());
            }
        });
    }
}