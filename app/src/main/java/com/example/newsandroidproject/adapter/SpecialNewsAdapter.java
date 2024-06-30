package com.example.newsandroidproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.NewsContentModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class SpecialNewsAdapter extends RecyclerView.Adapter<SpecialNewsAdapter.SpecialNewsHolder> {
    private Activity context;
    private List<ArticleInNewsFeedModel> newsContentModelList;
    private float textSize = 0;

    public SpecialNewsAdapter(Activity context, List<ArticleInNewsFeedModel> newsContentModelList) {
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
    private static final int REQUEST_CODE_GET_DATA = 111;
    @Override
    public void onBindViewHolder(@NonNull SpecialNewsAdapter.SpecialNewsHolder holder, int position) {
        if (newsContentModelList.get(position).getThumbnail() != null) {
            byte[] avatarByteData = Base64.decode(newsContentModelList.get(position).getThumbnail(), Base64.DEFAULT);
            holder.imgSpThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
        } else {
            holder.imgSpThumbnail.setImageResource(R.drawable.ic_blank_avatar);
        }

        holder.txtTitleSpNews.setText(newsContentModelList.get(position).getTitle());
        holder.txtReviewContentSpNews.setText(newsContentModelList.get(position).getDescription());
        holder.txtTitleSpNews.setTextSize(16+textSize);
        holder.txtReviewContentSpNews.setTextSize(14+textSize);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadingActivity.class);
                intent.putExtra("articleId", newsContentModelList.get(holder.getBindingAdapterPosition()).getArticleId());
                context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);
//                    Toast.makeText(context, "Bạn đã nhấn vào một bài báo! id" + articles.get(holder.getBindingAdapterPosition() - 1).getArticleId(), Toast.LENGTH_SHORT).show();
            }
        });

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
