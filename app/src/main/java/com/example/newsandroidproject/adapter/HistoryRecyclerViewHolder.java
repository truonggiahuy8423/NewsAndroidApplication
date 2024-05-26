package com.example.newsandroidproject.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;

public class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView imgViewThumbnail;
    TextView txtViewTittle, txtViewSource;
    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        imgViewThumbnail = itemView.findViewById(R.id.item_image);
        txtViewTittle = itemView.findViewById(R.id.item_tittle);
        txtViewSource = itemView.findViewById(R.id.item_content);
    }
}
