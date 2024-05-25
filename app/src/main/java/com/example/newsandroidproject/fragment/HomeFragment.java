package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.activity.LoginActivity;
import com.example.newsandroidproject.adapter.ArticleRecycleViewAdapter;
import com.example.newsandroidproject.adapter.CategoryRecycleViewAdapter;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.adapter.FilterSpinnerAdapterArray;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ArticleApi apiService;

    // View
    private Toolbar toolbar;
    private ImageButton nav_menu_button;
    private MenuItem logoItem;
    private MenuItem searchItem;
    private RecyclerView categories_recy;
    private Spinner filtersSpinner;

    // Data - Adapter - Query Data Func - Data Changed - Set Up Adapter
    //// Category
    ArrayList<String> categories;
    CategoryRecycleViewAdapter category_recycle_view_adapter;
    RecyclerView article_recycle_view;
    @SuppressLint("NotifyDataSetChanged")
    private void queryCategories() {
        categories.add("Tất cả");
        categories.add("Thời sự");
        categories.add("Chính trị");
        categories.add("Thế giới");
        categories.add("Khoa học");
        categories.add("Kinh tế");
        categories.add("Thể thao");
        categories.add("Công nghệ");
        categories.add("Giáo dục");
        categories.add("Pháp luật");
        categories.add("Sức khỏe");
        categories.add("Văn hóa");
        categories.add("Du lịch");
        categories.add("Giải trí");
        categories.add("Khám phá");
        categories.add("Đời sống");
        categories.add("Nghệ thuật");
        categories.add("Bất động sản");
        category_recycle_view_adapter.notifyDataSetChanged();
    }
    private void setUpCategoriesRecycleViewAdapter() {
        categories = new ArrayList<>();
        category_recycle_view_adapter = new CategoryRecycleViewAdapter(getActivity(), categories);
        categories_recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categories_recy.setAdapter(category_recycle_view_adapter);
    }
    ////Filter Items
    ArrayList<String> filters;
    FilterSpinnerAdapterArray filtersAdapter;
    private void queryFilters() {
        filters.add("Mới nhất");
        filters.add("Hôm nay");
        filters.add("Tuần qua");
        filters.add("Tháng qua");
        filtersAdapter.notifyDataSetChanged();
    }

    private void setUpFiltersSpinnerAdapter() {
        filters = new ArrayList<>();
        filtersAdapter = new FilterSpinnerAdapterArray(getActivity(), filters);
        filtersSpinner.setAdapter(filtersAdapter);
    }

    ////MinimalArticleModel
    ArrayList<ArticleInNewsFeedModel> articles;
    ArticleRecycleViewAdapter articlesAdapter;
    private void queryArticles() {
        articles.clear();
        ArticleApi apiService = RetrofitService.getClient(getContext()).create(ArticleApi.class);
        apiService.getArticlesInNewsFeed().enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                if (response.isSuccessful()) {
                    articles.addAll(response.body());
                    Log.d("Test API", "Error: " + response.body());
                    articlesAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<List<ArticleInNewsFeedModel>> call, Throwable t) {
                Log.d("Test API", "Failure: " + t.getMessage());
            }
        });
        //    public LiveData<List<ArticleInNewsFeedModel>> getArticlesInNewsFeed() {
//        final MutableLiveData<List<ArticleInNewsFeedModel>> data = new MutableLiveData<>();
//        return data;
//    }
//        repo.getArticlesInNewsFeed().observe(getViewLifecycleOwner(), new Observer<List<ArticleInNewsFeedModel>>() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onChanged(List<ArticleInNewsFeedModel> resultArticles) { // Trả về đối tượng DTO Response để check lỗi
//                if (resultArticles != null) {
//                    articles.addAll(resultArticles);
//                    Log.d("Test", String.valueOf(articles.size()));
//                    articlesAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }


    private void setUpArticlesRecycleViewAdapter() {
        articles = new ArrayList<>();
        articlesAdapter = new ArticleRecycleViewAdapter((MainActivity) getActivity(), articles);
        article_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        article_recycle_view.setAdapter(articlesAdapter);
    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);}



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnDocBao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void initiateAdapters() {
        setUpCategoriesRecycleViewAdapter();
        queryCategories();

        setUpFiltersSpinnerAdapter();
        queryFilters();

        setUpArticlesRecycleViewAdapter();
        queryArticles();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categories_recy = view.findViewById(R.id.categories_recy);
        toolbar = view.findViewById(R.id.tool_bar);
        nav_menu_button = view.findViewById(R.id.left_navigation_menu);
        filtersSpinner = view.findViewById(R.id.filters_spinner);
        article_recycle_view = view.findViewById(R.id.article_recycle_view);
        initiateAdapters();

        // Envent handler
        nav_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setOpenNavigationBar();
            }
        });
        return view;
    }
}