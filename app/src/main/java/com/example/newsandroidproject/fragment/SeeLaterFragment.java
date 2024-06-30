package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.FavoriteViewAdapter;
import com.example.newsandroidproject.api.FavoriteArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeeLaterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeeLaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeeLaterFragment newInstance(String param1, String param2) {
        SeeLaterFragment fragment = new SeeLaterFragment();
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

    RecyclerView seeLaterView;
    MainActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_seelater, container, false);

        seeLaterView = rootView.findViewById(R.id.seelater_recyclerView);

        seeLaterView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articles = new ArrayList<>();
        adapter = new FavoriteViewAdapter((MainActivity) getActivity(), articles, (MainActivity) getActivity());
        seeLaterView.setAdapter(adapter);

        queryArticles();

        ImageView backButton = rootView.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingFragment();
            }

        });

        return rootView;
    }

    private void openSettingFragment() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.openSettingFragment();
        }
    }

    ArrayList<ArticleInNewsFeedModel> articles;
    FavoriteViewAdapter adapter;

    private void queryArticles() {
        articles.clear();
        FavoriteArticleApi apiService = RetrofitService.getClient(getContext()).create(FavoriteArticleApi.class);
        apiService.getArticles().enqueue(new Callback<List<ArticleInNewsFeedModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<ArticleInNewsFeedModel>> call, Response<List<ArticleInNewsFeedModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articles.addAll(response.body());
                    for (ArticleInNewsFeedModel article : response.body()) {
                        Log.d("Test API", "Article: " + article.toString());
                    }
                    Log.d("Test API", "Error: " + response.body());
                    adapter.notifyDataSetChanged();

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
    }
}
