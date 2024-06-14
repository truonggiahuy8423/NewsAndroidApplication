package com.example.newsandroidproject.adapter;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.fragment.HistoryFragment;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;

import java.util.List;

public class ArticleRecycleViewAdapter extends RecyclerView.Adapter<ArticleRecycleViewAdapter.ArticleViewHolder> {

    private List<ArticleInNewsFeedModel> articles;
    private MainActivity context;

    public interface ArticleItemClickListener {
        void onArticleItemClick(String articleId);
    }

    private ArticleItemClickListener itemClickListener;

    public ArticleRecycleViewAdapter(MainActivity context, List<ArticleInNewsFeedModel> articles, ArticleItemClickListener itemClickListener) {
        this.context =  context;
        this.articles = articles;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_recycle_view_item, parent, false);
        return new ArticleViewHolder(view);
    }
    private static final int REQUEST_CODE_GET_DATA = 1;

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleInNewsFeedModel article = articles.get(position);

        // Set data to views
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewDescription.setText(article.getDescription());
        holder.textViewAuthorName.setText(article.getUserName());
        holder.textViewSubscriberCount.setText(article.getFollowCount() + "N người theo dõi");
        holder.textViewPostTime.setText(article.getCreateTime().toString());
        holder.textViewViewCount.setText(String.valueOf(article.getViewCount()));
        holder.textViewCommentCount.setText(String.valueOf(article.getCommentCount()));

        // Set images
        if (article.getThumbnail() != null) {
        byte[] thumbnailByteData = Base64.decode(article.getThumbnail(), Base64.DEFAULT);
        holder.imageViewThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length)); }
        if (article.getAvatar() != null) {
        byte[] avatarByteData = Base64.decode(article.getAvatar(), Base64.DEFAULT);
        holder.imageViewAuthor.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadingActivity.class);
                context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);
                Toast.makeText(context, "Bạn đã nhấn vào một bài báo!", Toast.LENGTH_SHORT).show();

                itemClickListener.onArticleItemClick(article.getArticleId().toString());
            }
        });

        holder.textViewPostTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Bạn đã nhấn vào một bài báo!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription, textViewAuthorName, textViewSubscriberCount,
                textViewPostTime, textViewViewCount, textViewCommentCount;
        ImageView imageViewThumbnail, imageViewAuthor;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.article_title);
            textViewDescription = itemView.findViewById(R.id.article_description);
            textViewAuthorName = itemView.findViewById(R.id.author_name);
            textViewSubscriberCount = itemView.findViewById(R.id.subscriber_count);
            textViewPostTime = itemView.findViewById(R.id.post_time);
            textViewViewCount = itemView.findViewById(R.id.view_count);
            textViewCommentCount = itemView.findViewById(R.id.comment_count);
            imageViewThumbnail = itemView.findViewById(R.id.imageView);
            imageViewAuthor = itemView.findViewById(R.id.author_image);
        }
    }
}
