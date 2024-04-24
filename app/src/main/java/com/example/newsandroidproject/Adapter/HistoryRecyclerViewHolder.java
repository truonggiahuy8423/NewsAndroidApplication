package com.example.newsandroidproject.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;

public class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView imgViewThumbnail, imgViewComment, imgViewBookmark;
    TextView txtViewTittle, txtViewSource, txtViewCount;
    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        imgViewThumbnail = itemView.findViewById(R.id.item_image);
        txtViewTittle = itemView.findViewById(R.id.item_tittle);
        txtViewSource = itemView.findViewById(R.id.item_content);
    }
}
