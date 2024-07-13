package com.example.newsandroidproject.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    private List<ArticleInNewsFeedModel> articleInNewsFeedModelList;

    Activity context;
    public SearchResultAdapter(List<ArticleInNewsFeedModel> articleInNewsFeedModelList, Activity context) {
        this.articleInNewsFeedModelList = articleInNewsFeedModelList;
        this.context = context;
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

        }
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        ArticleInNewsFeedModel article = articleInNewsFeedModelList.get(position);
        // Glide.with(holder.itemView.getContext())
        //         .load(article.getThumbnail())
        //         .into(holder.sivThumbnailUserInfo);
        if (article.getThumbnail() == null) {
            holder.sivThumbnailUserInfo.setImageResource(R.drawable.default_img);
        }
        else{
            byte[] thumbnailByteData = Base64.decode(article.getThumbnail(), Base64.DEFAULT);
            holder.sivThumbnailUserInfo.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        }
         holder.tvTitleArtiUserInfo.setText(article.getTitle());
        holder.tvDateArtiUserInfo.setText(DateParser.dateFormat(article.getCreateTime()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadingActivity.class);
                intent.putExtra("articleId", articleInNewsFeedModelList.get(holder.getBindingAdapterPosition()).getArticleId());
                context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);
//                    Toast.makeText(context, "Bạn đã nhấn vào một bài báo! id" + articles.get(holder.getBindingAdapterPosition() - 1).getArticleId(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private static int REQUEST_CODE_GET_DATA = 2182;
    @Override
    public int getItemCount() {
        return articleInNewsFeedModelList.size();
    }
}