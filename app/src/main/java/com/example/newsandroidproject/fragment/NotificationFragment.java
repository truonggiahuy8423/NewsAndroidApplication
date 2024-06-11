package com.example.newsandroidproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsandroidproject.databinding.FragmentNotificationBinding;
import com.example.newsandroidproject.adapter.NotificationAdapter;
import com.example.newsandroidproject.model.dto.NotificationDTO;
import com.example.newsandroidproject.api.NotificationApi;
import com.example.newsandroidproject.retrofit.RetrofitService;

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

        // get notification from server
        Long user_id = 1L;
        NotificationApi apiService = RetrofitService.getClient(getContext()).create(NotificationApi.class);
        apiService.getNotification(user_id).enqueue(new retrofit2.Callback<java.util.List<NotificationDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<java.util.List<NotificationDTO>> call, retrofit2.Response<java.util.List<NotificationDTO>> response) {
                if (response.isSuccessful()) {
                    java.util.List<NotificationDTO> notifications = response.body();
                    // update notification list
                    NotificationAdapter adapter = (NotificationAdapter) binding.notificationRecyclerView.getAdapter();
                    assert adapter != null;
                    adapter.setNotifications(notifications);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<java.util.List<NotificationDTO>> call, Throwable t) {
                // show error message
            }
        });


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