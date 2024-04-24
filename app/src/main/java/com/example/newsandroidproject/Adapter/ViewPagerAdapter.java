package com.example.newsandroidproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.VideoViewHolder>{
    private List<Integer> colors;

    public ViewPagerAdapter() {
        colors = new ArrayList<>();
        colors.add(R.color.black);
        colors.add(R.color.purple_200);
        colors.add(R.color.teal_200);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(colors.get(position % 3)));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
