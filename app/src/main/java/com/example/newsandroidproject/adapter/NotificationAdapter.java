package com.example.newsandroidproject.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Activity context;
    private List<NotificationDTO> notifications;

    public NotificationAdapter(Activity context, List<NotificationDTO> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationDTO notification = notifications.get(position);

        holder.title.setText(notification.getContent());
        holder.time.setText("1h trước"); // Thay thế bằng dữ liệu thực tế

        // Sử dụng Glide để tải ảnh
        if (notification.getThumbnail() != null) {
            byte[] thumbnailByteData = Base64.decode(notification.getThumbnail(), Base64.DEFAULT);
            holder.imgArticle.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        } else {
            holder.imgArticle.setImageResource(R.drawable.default_img);
        }

        if (notification.getThumbnail() != null) {
            byte[] thumbnailByteData = Base64.decode(notification.getThumbnail(), Base64.DEFAULT);
            holder.imgAvar.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        } else {
            holder.imgAvar.setImageResource(R.drawable.default_img);
        }


        // Thay đổi icon dựa trên type
        switch (notification.getType()) {
            case 1:
                holder.imgNotiIcon.setImageResource(R.drawable.bookmark_fill_svgrepo_com);
                break;
            case 2:
                holder.imgNotiIcon.setImageResource(R.drawable.ic_cmt);
                break;
            case 3:
                holder.imgNotiIcon.setImageResource(R.drawable.ic_liked2);
                break;
            case 4:
                holder.imgNotiIcon.setImageResource(R.drawable.ic_cmt);
                break;
            default:
                holder.imgNotiIcon.setImageResource(R.drawable.default_img); // Icon mặc định nếu không có type phù hợp
                break;
        }
    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public int getItemViewType(int position) {
        return notifications.get(position).getType();
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các View
        public ImageView imgAvar;
        public ImageView imgArticle;
        public ImageView imgNotiIcon;
        public TextView time;
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các View với findViewById
            imgAvar = itemView.findViewById(R.id.imgAvar);
            imgArticle = itemView.findViewById(R.id.imgArticle);
            imgNotiIcon = itemView.findViewById(R.id.imgNotiIcon);
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