package com.example.newsandroidproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.databinding.FragmentNotificationBinding;
import com.example.newsandroidproject.adapter.NotificationAdapter;
import com.example.newsandroidproject.model.dto.NotificationDTO;
import com.example.newsandroidproject.api.NotificationApi;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.util.List;

public class NotificationFragment extends Fragment {


    // binding
     private FragmentNotificationBinding binding;

    public NotificationFragment() {
        // Required empty public constructor
    }

    NotificationAdapter adapter;
    UniqueList<NotificationDTO> notifications;
    @SuppressLint("NotifyDataSetChanged")
    public void setNotis(List<NotificationDTO> notis) {
        notifications.clear();
        notifications.addAll(notis);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNotificationBinding.inflate(inflater, container, false);

        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notifications = new UniqueList<>();

        binding.notificationRecyclerView.setAdapter(new NotificationAdapter(getActivity(), notifications));

        adapter = (NotificationAdapter) binding.notificationRecyclerView.getAdapter();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}