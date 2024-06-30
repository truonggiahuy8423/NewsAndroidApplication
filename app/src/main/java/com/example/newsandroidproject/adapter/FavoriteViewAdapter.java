package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.FavoriteViewItemModel;

import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewHolder>{
    Context context;
    List<FavoriteViewItemModel> items;

    public FavoriteViewAdapter(Context context, List<FavoriteViewItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FavoriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerViewHolder holder, int position) {
        holder.txtViewTittle.setText(items.get(position).getTittle());
        holder.txtViewSource.setText(items.get(position).getContent());
        holder.imgViewThumbnail.setImageResource(items.get(position).getThumbnailImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
