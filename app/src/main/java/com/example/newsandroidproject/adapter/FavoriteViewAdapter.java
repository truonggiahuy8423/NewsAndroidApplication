package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.FavoriteViewItemModel;

import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewHolder>{
    private MainActivity context;
    List<ArticleInNewsFeedModel> items;

    public interface FavoriteArticleItemClickListener {
        void onArticleItemClick(String articleId);
    }

    private FavoriteViewAdapter.FavoriteArticleItemClickListener itemClickListener;


    public FavoriteViewAdapter(MainActivity context, List<ArticleInNewsFeedModel> items, FavoriteArticleItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FavoriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_view, parent, false));
    }

    private static final int REQUEST_CODE_GET_DATA = 1;

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerViewHolder holder, int position) {
        holder.txtViewTittle.setText(items.get(position).getTitle());
        holder.txtViewSource.setText(items.get(position).getDescription());
        if (items.get(position).getThumbnail() != null) {
            byte[] thumbnailByteData = Base64.decode(items.get(position).getThumbnail(), Base64.DEFAULT);
            holder.imgViewThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadingActivity.class);
                context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);

                itemClickListener.onArticleItemClick(items.get(position).getArticleId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
