package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.adapter.HistoryViewAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.HistoryArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.HistoryViewItemModel;
import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<HistoryViewItemModel> articles;
    ArrayList<Long> convertArticleIDs = new ArrayList<>();

    public HistoryFragment(){

    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<Long> idList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (getArguments() != null) {
            ArrayList<String> articleIDs = getArguments().getStringArrayList("articleIDs");
            Log.d("HistoryFragment", "Received article IDs: " + articleIDs.toString());

            for (String id : articleIDs) {
                convertArticleIDs.add(Long.parseLong(id));
            }

        }
    }

    RecyclerView historyView;
    MainActivity context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        historyView = rootView.findViewById(R.id.history_recyclerView);
        ImageView backButton = rootView.findViewById(R.id.back_button);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingFragment();
            }

        });

        Retrofit retrofit = RetrofitService.getClient(getContext());
        historyArticleApi = retrofit.create(HistoryArticleApi.class);

        historyView.setLayoutManager(new LinearLayoutManager(context));
        articles = new ArrayList<>();
        historyAdapter = new HistoryViewAdapter((MainActivity) getActivity(), articles);
        historyView.setAdapter(historyAdapter);

        fetchArticlesByIds(convertArticleIDs);

        return rootView;
    }

    private void openSettingFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openSettingFragment();
        }
    }

    List<HistoryViewItemModel> historyItems;
    HistoryViewAdapter historyAdapter;

    private HistoryArticleApi historyArticleApi;

    private void fetchArticlesByIds(ArrayList<Long> idList) {
        Call<List<HistoryViewItemModel>> call = historyArticleApi.getArticleByIds(idList);
        call.enqueue(new Callback<List<HistoryViewItemModel>>() {
            @Override
            public void onResponse(Call<List<HistoryViewItemModel>> call, Response<List<HistoryViewItemModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HistoryViewItemModel> fetchedArticles = response.body();
                    articles.clear();
                    articles.addAll(fetchedArticles);
                    historyAdapter.notifyDataSetChanged();

                    Context context = getContext();
                    if (context != null) {
                        Toast.makeText(context, "Number of articles: " + articles.size(), Toast.LENGTH_SHORT).show();
                    }

                    Log.d("HistoryFragment", "Number of elements: " + articles.size());
                } else {
                    String errorMessage = "";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("HistoryFragment", "Response unsuccessful: " + response.code() + " " + response.message() + " " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<HistoryViewItemModel>> call, Throwable t) {
                Log.e("HistoryFragment", "API call failed", t);
                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
