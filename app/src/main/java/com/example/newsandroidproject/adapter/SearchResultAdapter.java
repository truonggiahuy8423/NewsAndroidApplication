package com.example.newsandroidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    private List<ArticleInNewsFeedModel> articleInNewsFeedModelList;

    public SearchResultAdapter(List<ArticleInNewsFeedModel> articleInNewsFeedModelList) {
        this.articleInNewsFeedModelList = articleInNewsFeedModelList;
    }

    class SearchResultHolder extends RecyclerView.ViewHolder {
        ShapeableImageView sivThumbnailUserInfo;
        TextView tvTitleArtiUserInfo;
        TextView tvDateArtiUserInfo;
        TextView tvNoViewedArtiUserInfo;
        ImageView ivViewArtiUserInfo;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            sivThumbnailUserInfo = itemView.findViewById(R.id.sivThumbnailUserInfo);
            tvTitleArtiUserInfo = itemView.findViewById(R.id.tv_title_arti_user_info);
            tvDateArtiUserInfo = itemView.findViewById(R.id.tv_date_arti_user_info);
            tvNoViewedArtiUserInfo = itemView.findViewById(R.id.tv_no_viewed_arti_user_info);
            ivViewArtiUserInfo = itemView.findViewById(R.id.iv_view_arti_user_info);
        }
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultHolder holder, int position) {
        ArticleInNewsFeedModel article = articleInNewsFeedModelList.get(position);
        // Glide.with(holder.itemView.getContext())
        //         .load(article.getThumbnail())
        //         .into(holder.sivThumbnailUserInfo);
        // holder.tvTitleArtiUserInfo.setText(article.getTitle());
//        holder.tvDateArtiUserInfo.setText(DateParser.parseFromISO8601(article.get()));
        holder.tvNoViewedArtiUserInfo.setText(String.valueOf(article.getViewCount()));

    }

    @Override
    public int getItemCount() {
        return articleInNewsFeedModelList.size();
    }
}