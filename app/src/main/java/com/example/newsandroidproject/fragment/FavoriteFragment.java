package com.example.newsandroidproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.adapter.FavoriteViewAdapter;
import com.example.newsandroidproject.adapter.HistoryViewAdapter;
import com.example.newsandroidproject.model.viewmodel.FavoriteViewItemModel;
import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
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
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView favoriteView = rootView.findViewById(R.id.favorite_recyclerView);

        List<FavoriteViewItemModel> items = new ArrayList<FavoriteViewItemModel>();

        for (int i = 0; i < 7; i++)
        {
            FavoriteViewItemModel newItem = new FavoriteViewItemModel(R.drawable.thumbnail_image,
                    "This is a test string",
                    "This is a long text that will automatically wrap, This is a long text that will automatically wrap.");
            items.add(newItem);
        }

        Context context = getActivity();
        favoriteView.setLayoutManager(new LinearLayoutManager(context));
        favoriteView.setAdapter(new FavoriteViewAdapter(context.getApplicationContext(), items));

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
}