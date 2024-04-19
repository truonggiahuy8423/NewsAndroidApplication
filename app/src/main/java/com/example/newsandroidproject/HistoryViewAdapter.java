package com.example.newsandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {

    Context context;
    List<HistoryViewItem> items;

    public HistoryViewAdapter(Context context, List<HistoryViewItem> items) {
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
        holder.txtViewTittle.setText(items.get(position).getTittle());
        holder.txtViewSource.setText(items.get(position).getContent());
        holder.imgViewThumbnail.setImageResource(items.get(position).getThumbnailImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
