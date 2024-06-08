package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.viewmodel.NewsContentModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class NewsContentAdapter extends RecyclerView.Adapter<NewsContentAdapter.NewsContentHolder> {
    private Context context;
    private List<BodyItem> bodyItemList;
    private float textSize = 0;

    public NewsContentAdapter(Context context, List<BodyItem> bodyItemList) {
        this.context = context;
        this.bodyItemList = bodyItemList;
    }

    public void setTextSize(int txtSize){
        this.textSize = txtSize-14;
    }
    @NonNull
    @Override
    public NewsContentAdapter.NewsContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_readingpage, parent, false);
        return new NewsContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsContentHolder holder, int position) {
        BodyItem bodyItem = bodyItemList.get(position);
        Log.d("Huy", bodyItemList.get(0).getArticleTitle());
        if (position == 0) {
            holder.txtTitle_0.setVisibility(View.VISIBLE);
            holder.txtTitle_0.setText(bodyItem.getArticleTitle());
        } else {
            if (bodyItem.getArticleTitle() == null || bodyItem.getArticleTitle().equals("")) {
                holder.txtTitle_0.setVisibility(View.GONE);
            } else {
                holder.txtTitle_0.setText(bodyItem.getArticleTitle());
            }
        }

        if (bodyItem.getBodyTitle() == null || bodyItem.getBodyTitle().equals("")){
            holder.txtTitle_1.setVisibility(View.GONE);
        } else {
            holder.txtTitle_1.setText(bodyItem.getBodyTitle());
        }

        if (bodyItem.getContent() == null || bodyItem.getContent().equals("")){
            holder.txtContent_0.setVisibility(View.GONE);
        } else {
            holder.txtContent_0.setText(bodyItem.getContent());
        }

        if (position == 0) {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.txtImgContent.setVisibility(View.VISIBLE);
            holder.txtImgContent.setText(bodyItem.getImageName());
            if (bodyItem.getDataImage() != null) {
                Log.d("Huy", "Delete" + bodyItemList.size());
                byte[] thumbnailByteData = Base64.decode(bodyItem.getDataImage(), Base64.DEFAULT);
                holder.imgNews.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
            } else {
                holder.imgNews.setImageResource(R.drawable.default_img);
            }
        } else {
            if(bodyItem.getImageName() == null || bodyItem.getImageName().equals("")){
            holder.cardView.setVisibility(View.GONE);
            holder.txtImgContent.setVisibility(View.GONE);
        }else{
            holder.txtImgContent.setText(bodyItem.getImageName());
            if (bodyItem.getDataImage() != null) {
                Log.d("Huy", "Delete" + bodyItemList.size());
                byte[] thumbnailByteData = Base64.decode(bodyItem.getDataImage(), Base64.DEFAULT);
                holder.imgNews.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
            } else {
                holder.imgNews.setImageResource(R.drawable.default_img);
            }
        }}



        holder.txtTitle_0.setTextSize(20+textSize);
        holder.txtTitle_1.setTextSize(16+textSize);
        holder.txtContent_0.setTextSize(14+textSize);
        holder.txtImgContent.setTextSize(14+textSize);
    }

    @Override
    public int getItemCount() {
        return bodyItemList.size();
    }

    public static class NewsContentHolder extends RecyclerView.ViewHolder{
        TextView txtTitle_0,txtTitle_1, txtContent_0, txtContent_1, txtImgContent;
        ImageView imgNews;
        CardView cardView;

        public NewsContentHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle_0 = itemView.findViewById(R.id.txtTitle_0);
            txtTitle_1 = itemView.findViewById(R.id.txtTitle_1);
            txtContent_0 = itemView.findViewById(R.id.txtContent_0);
            txtImgContent = itemView.findViewById(R.id.txtImgContent);
            imgNews = itemView.findViewById(R.id.imgNews);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
