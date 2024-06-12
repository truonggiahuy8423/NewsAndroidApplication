package com.example.newsandroidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.NumParser;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;

import java.util.List;
public class ArticleRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final int REQUEST_CODE_GET_DATA = 1;


    private List<ArticleInNewsFeedModel> articles;
    private MainActivity context;

    public ArticleRecycleViewAdapter(MainActivity context, List<ArticleInNewsFeedModel> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        return articles.size() == position ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_recycle_view_item, parent, false);
            return new ArticleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ArticleViewHolder) {
            ArticleInNewsFeedModel article = articles.get(position);
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;

            // Set data to views
            articleViewHolder.textViewTitle.setText(article.getTitle());
            articleViewHolder.textViewDescription.setText(article.getDescription());
            articleViewHolder.textViewAuthorName.setText(article.getUserName());
            articleViewHolder.textViewSubscriberCount.setText(NumParser.numParse(article.getFollowCount()) + " người theo dõi");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                articleViewHolder.textViewPostTime.setText(DateParser.timeSince(article.getModifyTime()));
            } else {
                articleViewHolder.textViewPostTime.setText(DateParser.dateFormat(article.getModifyTime()));
            }
            articleViewHolder.textViewViewCount.setText(String.valueOf(article.getViewCount()));
            articleViewHolder.textViewCommentCount.setText(String.valueOf(article.getCommentCount()));

            // Set images
            if (article.getThumbnail() != null) {
                byte[] thumbnailByteData = Base64.decode(article.getThumbnail(), Base64.DEFAULT);
                articleViewHolder.imageViewThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
            } else {
                articleViewHolder.imageViewThumbnail.setImageResource(R.drawable.default_img);
            }
            if (article.getAvatar() != null) {
                byte[] avatarByteData = Base64.decode(article.getAvatar(), Base64.DEFAULT);
                articleViewHolder.imageViewAuthor.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
            } else {
                articleViewHolder.imageViewAuthor.setImageResource(R.drawable.ic_blank_avatar);
            }

            articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReadingActivity.class);
                    intent.putExtra("articleId", articles.get(position).getArticleId());
                    context.startActivityForResult(intent, REQUEST_CODE_GET_DATA);
                    Toast.makeText(context, "Bạn đã nhấn vào một bài báo!", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size() + 1;
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

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

//    public void addLoadingView() {
//        articles.add(null);
//        notifyItemInserted(articles.size() - 1);
//    }
//
//    public void removeLoadingView() {
//        System.out.println("removeLoadingView");
//        if (articles.size() != 0) {
//            articles.remove(articles.size() - 1);
//            notifyItemRemoved(articles.size());
//        }
//    }

    public void addArticles(List<ArticleInNewsFeedModel> newArticles) {
        int oldSize = articles.size();
        System.out.println("start " + oldSize);
        articles.addAll(newArticles);
        System.out.println("end " + (articles.size() - oldSize));

        notifyItemRangeInserted(oldSize, articles.size() - oldSize);
    }
}
