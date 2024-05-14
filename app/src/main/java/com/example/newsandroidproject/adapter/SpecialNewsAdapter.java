package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.viewmodel.NewsContentModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class SpecialNewsAdapter extends RecyclerView.Adapter<SpecialNewsAdapter.SpecialNewsHolder> {
    private Context context;
    private List<NewsContentModel> newsContentModelList;
    private float textSize = 0;

    public SpecialNewsAdapter(Context context, List<NewsContentModel> newsContentModelList) {
        this.context = context;
        this.newsContentModelList = newsContentModelList;
    }

    public void setTextSize(int txtSize){
        this.textSize = txtSize-16;
    }
    @NonNull
    @Override
    public SpecialNewsAdapter.SpecialNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecialNewsHolder(LayoutInflater.from(context).inflate(R.layout.item_specialnews, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialNewsAdapter.SpecialNewsHolder holder, int position) {
        holder.imgSpThumbnail.setImageResource(newsContentModelList.get(position).getImgNews());
        holder.txtTitleSpNews.setText(newsContentModelList.get(position).getTitle_0());
        holder.txtReviewContentSpNews.setText(newsContentModelList.get(position).getTitle_1());
        holder.txtTitleSpNews.setTextSize(16+textSize);
        holder.txtReviewContentSpNews.setTextSize(14+textSize);

    }

    @Override
    public int getItemCount() {
        return newsContentModelList.size();
    }

    public class SpecialNewsHolder extends RecyclerView.ViewHolder{
        ImageView imgSpThumbnail;
        TextView txtTitleSpNews, txtReviewContentSpNews;
        public SpecialNewsHolder(@NonNull View itemView) {
            super(itemView);
            imgSpThumbnail = itemView.findViewById(R.id.imgSpThumbnail);
            txtTitleSpNews = itemView.findViewById(R.id.txtTitleSpNews);
            txtReviewContentSpNews = itemView.findViewById(R.id.txtReviewContentSpNews);
        }
    }
}
