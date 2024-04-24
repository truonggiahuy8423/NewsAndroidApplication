package com.example.newsandroidproject.Adapter;

import android.content.Intent;
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
import com.example.newsandroidproject.Activity.ReadingActivity;
import com.example.newsandroidproject.ViewModel.MinimalArticleModel;

import java.util.List;

public class ArticleRecycleViewAdapter extends RecyclerView.Adapter<ArticleRecycleViewAdapter.ArticleViewHolder> {

    private List<MinimalArticleModel> articles;
    private MainActivity context;

    public ArticleRecycleViewAdapter(MainActivity context, List<MinimalArticleModel> articles) {
        this.context =  context;
        this.articles = articles;
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
        MinimalArticleModel article = articles.get(position);

        // Set data to views
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewDescription.setText(article.getDescription());
        holder.textViewAuthorName.setText(article.getAuthor_name());
        holder.textViewSubscriberCount.setText(article.getSubscribe_count() + "N người theo dõi");
        holder.textViewPostTime.setText(article.getPost_time().toString());
        holder.textViewViewCount.setText(String.valueOf(article.getView_count()));
        holder.textViewCommentCount.setText(String.valueOf(article.getcomment_count()));

        // Set images
        holder.imageViewThumbnail.setImageBitmap(article.getThumbnail());
        holder.imageViewAuthor.setImageBitmap(article.getAuthor_image());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadingActivity.class);
                context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);
                Toast.makeText(context, "Bạn đã nhấn vào một bài báo!", Toast.LENGTH_SHORT).show();

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
