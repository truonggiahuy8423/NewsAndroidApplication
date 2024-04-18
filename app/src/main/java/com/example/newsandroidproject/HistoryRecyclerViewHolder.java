package com.example.newsandroidproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView imgViewThumbnail, imgViewComment, imgViewBookmark;
    TextView txtViewTittle, txtViewSource, txtViewCount;
    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        imgViewThumbnail = itemView.findViewById(R.id.item_image);
        imgViewComment = itemView.findViewById(R.id.item_comment);
        imgViewBookmark = itemView.findViewById(R.id.item_bookmark);
        txtViewTittle = itemView.findViewById(R.id.item_tittle);
        txtViewSource = itemView.findViewById(R.id.item_source);
        txtViewCount = itemView.findViewById(R.id.item_comment_count);
    }
}
