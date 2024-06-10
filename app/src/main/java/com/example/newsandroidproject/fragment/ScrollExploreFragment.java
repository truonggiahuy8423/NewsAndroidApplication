package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.ViewPagerAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleScrollPageModel;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollExploreFragment extends Fragment {
    private ArticleApi apiService;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<ArticleScrollPageModel> articleList;
    private int page_index = 1;
    public ScrollExploreFragment() {
        // Required empty public constructor
    }

    public static ScrollExploreFragment newInstance() {
        ScrollExploreFragment fragment = new ScrollExploreFragment();
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
        View root = inflater.inflate(R.layout.fragment_scroll_explore, container, false);
        viewPager = root.findViewById(R.id.vpExplore);
        setUpAdapter();
        loadDataFromApi();

        return root;
    }

    private void setUpAdapter() {
        articleList = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter((MainActivity) getActivity(),articleList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == viewPagerAdapter.getItemCount() - 1) {
                    // Người dùng đã kéo đến item cuối cùng
                    Log.d("Test", "Đã đến item cuối cùng");
                    loadDataFromApi();
                }
            }
        });
    }

    private void loadDataFromApi() {
        apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
        apiService.getArticlesInScrollPage(page_index++).enqueue(new Callback<List<ArticleScrollPageModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<ArticleScrollPageModel>> call, Response<List<ArticleScrollPageModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0){
                        articleList.addAll(response.body());
                        Log.d("Test API", "Error: " + response.body());
                        viewPagerAdapter.notifyDataSetChanged();
                    }
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