package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.ArticleScrollPageModel;
import com.example.newsandroidproject.model.viewmodel.BodyItemModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.VideoViewHolder>{
    private Context context;
    private List<ArticleScrollPageModel> articleScrollPageModelList;

    public ViewPagerAdapter(Context context, List<ArticleScrollPageModel> articleScrollPageModelList) {
        this.context = context;
        this.articleScrollPageModelList = articleScrollPageModelList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_scroll_article,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ArticleScrollPageModel article = articleScrollPageModelList.get(position);

        holder.txtNoViewedScroll.setText(String.valueOf(article.getViewCount()));
        holder.txtNoCommentedScroll.setText(String.valueOf(article.getCommentCount()));
        holder.txtUserNameScroll.setText(String.valueOf(article.getUserName()));
        holder.txtArticleTitle.setText(article.getTitle());
        holder.txtThumbnailName.setText(article.getThumbnailName());

        // TODO: Set Description
        if(article.getDescription() == null){
            holder.txtDescription.setVisibility(View.GONE);
        }
        else {
            holder.txtDescription.setText(article.getDescription());
        }

        // TODO: Set date
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        String formattedDate = formatter.format(article.getCreateTime());
        holder.txtDateScroll.setText(formattedDate);

        // TODO: Set images
        if (article.getThumbnail() == null) {
            holder.ivThumbnail.setVisibility(View.GONE);
            holder.cvThumbnail.setVisibility(View.GONE);
            holder.txtThumbnailName.setVisibility(View.GONE);
        }
        else{
            holder.ivThumbnail.setVisibility(View.VISIBLE);
            holder.cvThumbnail.setVisibility(View.VISIBLE);
            holder.txtThumbnailName.setVisibility(View.VISIBLE);
            byte[] thumbnailByteData = Base64.decode(article.getThumbnail(), Base64.DEFAULT);
            holder.ivThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
            holder.txtThumbnailName.setText(article.getThumbnailName());
        }
        if (article.getAvatar() != null) {
            byte[] avatarByteData = Base64.decode(article.getAvatar(), Base64.DEFAULT);
            holder.imgAvar.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
        }

        // TODO: Set content
        BodyItemScrollAdapter bodyItemScrollAdapter = new BodyItemScrollAdapter(this.context, article.getBodyItemList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.rvBodyItemScroll.setLayoutManager(linearLayoutManager);
        holder.rvBodyItemScroll.setAdapter(bodyItemScrollAdapter);

        // TODO: Set follower
        Long n_followers = article.getFollowCount();
        String followers = "";
        if (n_followers < 1000) {
            followers += String.valueOf(n_followers) + " người theo dõi";
        }
        else if(n_followers < 1000000){
            followers += String.valueOf(n_followers/1000) + "N người theo dõi";
        }
        else{
            followers += String.valueOf(n_followers/1000000) + "M người theo dõi";
        }
        holder.txtFollowerScroll.setText(followers);

        // TODO: Set favourite
        holder.txtNoSavedScroll.setText(String.valueOf(article.getFavoriteCount()));
        final boolean[] isUnFavourite = {true};
        holder.ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUnFavourite[0]){
                    holder.ivFavourite.setImageResource(R.drawable.ic_bookmarked_unsaved);
                    isUnFavourite[0] = false;
                }
                else{
                    holder.ivFavourite.setImageResource(R.drawable.ic_bookmarked_saved);
                    isUnFavourite[0] = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleScrollPageModelList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDateScroll, txtNoViewedScroll, txtUserNameScroll, txtFollowerScroll, txtNoSavedScroll, txtNoCommentedScroll, txtArticleTitle, txtThumbnailName, txtDescription;
        private CardView cvThumbnail;
        private ShapeableImageView imgAvar, ivThumbnail;
        private ImageView ivFavourite, ivSeeLater;
        private  RecyclerView rvBodyItemScroll;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateScroll = itemView.findViewById(R.id.txtDateScroll);
            txtNoViewedScroll = itemView.findViewById(R.id.txtNoViewedScroll);
            txtUserNameScroll = itemView.findViewById(R.id.txtUserNameScroll);
            txtFollowerScroll  = itemView.findViewById(R.id.txtFollowerScroll);
            txtNoSavedScroll = itemView.findViewById(R.id.txtNoSavedScroll);
            txtNoCommentedScroll = itemView.findViewById(R.id.txtNoCommentedScroll);
            txtArticleTitle = itemView.findViewById(R.id.txtArticleTitle);
            txtThumbnailName = itemView.findViewById(R.id.txtThumbnailName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            cvThumbnail = itemView.findViewById(R.id.cvThumbnail);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            imgAvar = itemView.findViewById(R.id.imgAvar);
            ivFavourite = itemView.findViewById(R.id.ivFavourite);
            ivSeeLater = itemView.findViewById(R.id.ivSeeLater);
            rvBodyItemScroll = itemView.findViewById(R.id.rvBodyItemScroll);
        }
    }
}
