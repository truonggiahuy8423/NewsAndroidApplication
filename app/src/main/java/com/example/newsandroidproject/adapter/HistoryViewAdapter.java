package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.model.viewmodel.HistoryViewItemModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {

    Context context;
    List<HistoryViewItemModel> items;

    public HistoryViewAdapter(Context context, List<HistoryViewItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewHolder holder, int position) {
        holder.txtViewTittle.setText(items.get(position).getTitle());
        holder.txtViewSource.setText(items.get(position).getDescription());
        if (items.get(position).getThumbnail() != null) {
            byte[] thumbnailByteData = Base64.decode(items.get(position).getThumbnail(), Base64.DEFAULT);
            holder.imgViewThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
