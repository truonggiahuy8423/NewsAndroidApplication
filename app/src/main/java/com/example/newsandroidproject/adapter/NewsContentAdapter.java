package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.model.viewmodel.NewsContentModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class NewsContentAdapter extends RecyclerView.Adapter<NewsContentAdapter.NewsContentHolder> {
    private Context context;
    private List<NewsContentModel> newsContentModelLists;
    private float textSize = 0;

    public NewsContentAdapter(Context context, List<NewsContentModel> newsContentModelLists) {
        this.context = context;
        this.newsContentModelLists = newsContentModelLists;
    }

    public void setTextSize(int txtSize){
        this.textSize = txtSize-16;
    }
    @NonNull
    @Override
    public NewsContentAdapter.NewsContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article_content, parent, false);
        return new NewsContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsContentHolder holder, int position) {
        NewsContentModel newsContentModel = newsContentModelLists.get(position);
        if(newsContentModel.getTitle_0() == null){
            holder.txtTitle_0.setVisibility(View.GONE);
        }else{
            holder.txtTitle_0.setText(newsContentModel.getTitle_0());
        }

        if(newsContentModel.getTitle_1() == null){
            holder.txtTitle_1.setVisibility(View.GONE);
        }else{
            holder.txtTitle_1.setText(newsContentModel.getTitle_1());
        }

        if(newsContentModel.getContent_0() == null){
            holder.txtContent_0.setVisibility(View.GONE);
        }else{
            holder.txtContent_0.setText(newsContentModel.getContent_0());
        }

        if(newsContentModel.getImgNews() == null){
            holder.imgNews.setVisibility(View.GONE);
        }else{
            holder.imgNews.setImageResource(newsContentModel.getImgNews());
        }

        if(newsContentModel.getTxtImgContent() == null){
            holder.txtImgContent.setVisibility(View.GONE);
        }else{
            holder.txtImgContent.setText(newsContentModel.getTxtImgContent());
        }

        if(newsContentModel.getContent_1() == null){
            holder.txtContent_1.setVisibility(View.GONE);
        }else{
            holder.txtContent_1.setText(newsContentModel.getContent_1());
        }

        holder.txtTitle_0.setTextSize(20+textSize);
        holder.txtTitle_1.setTextSize(16+textSize);
        holder.txtContent_0.setTextSize(14+textSize);
        holder.txtContent_1.setTextSize(14+textSize);
        holder.txtImgContent.setTextSize(14+textSize);
    }

    @Override
    public int getItemCount() {
        return newsContentModelLists.size();
    }

    public class NewsContentHolder extends RecyclerView.ViewHolder{
        TextView txtTitle_0,txtTitle_1, txtContent_0, txtContent_1, txtImgContent;
        ImageView imgNews;

        public NewsContentHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle_0 = itemView.findViewById(R.id.txtTitle_0);
            txtTitle_1 = itemView.findViewById(R.id.txtTitle_1);
            txtContent_0 = itemView.findViewById(R.id.txtContent_0);
            txtContent_1 = itemView.findViewById(R.id.txtContent_1);
            txtImgContent = itemView.findViewById(R.id.txtImgContent);
            imgNews = itemView.findViewById(R.id.imgNews);
        }
    }
}
