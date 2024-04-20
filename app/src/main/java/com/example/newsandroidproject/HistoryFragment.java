package com.example.newsandroidproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView historyView = rootView.findViewById(R.id.history_recyclerView);

        List<HistoryViewItemModel> items = new ArrayList<HistoryViewItemModel>();

        for (int i = 0; i < 7; i++)
        {
            HistoryViewItemModel newItem = new HistoryViewItemModel(R.drawable.thumbnail_image,
                    "This is a test string",
                    "This is a long text that will automatically wrap, This is a long text that will automatically wrap.");
            items.add(newItem);
        }

        Context context = getActivity();
        historyView.setLayoutManager(new LinearLayoutManager(context));
        historyView.setAdapter(new HistoryViewAdapter(context.getApplicationContext(), items));

        return rootView;
    }

}
