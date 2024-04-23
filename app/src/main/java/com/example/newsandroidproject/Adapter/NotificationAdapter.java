package com.example.newsandroidproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications;

    public NotificationAdapter() {
        this.notifications = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            this.notifications.add(new Notification("Pháp Luật", "1h trước", "Nghi phạm cầm đầu vụ cướp tiệm vàng ở Bình Dương bị bắt"));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.category.setText(notification.getCategory());
        holder.time.setText(notification.getTime());
        holder.title.setText(notification.getTitle());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, time, title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
        }
    }

    public static class Notification {
        private String category, time, title;

        public Notification(String category, String time, String title) {
            this.category = category;
            this.time = time;
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public String getTime() {
            return time;
        }

        public String getTitle() {
            return title;
        }
    }
}