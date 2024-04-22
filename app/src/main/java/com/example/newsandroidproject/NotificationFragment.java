package com.example.newsandroidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.databinding.FragmentNotificationBinding;
import com.example.newsandroidproject.NotificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // binding
     private FragmentNotificationBinding binding;

    public NotificationFragment() {
        // Required empty public constructor
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
        binding.notificationRecyclerView.setAdapter(new NotificationAdapter());
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}