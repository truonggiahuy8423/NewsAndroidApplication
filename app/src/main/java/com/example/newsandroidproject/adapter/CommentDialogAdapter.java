package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;

import java.util.List;
public class CommentDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_COMMENT = 0;
    private static final int VIEW_TYPE_LOAD_MORE = 1;

    private Context context;
    private List<UserCommentDTO> commentItemModelList;
    private boolean isLoadMoreButtonVisible;

    private View.OnClickListener loadMoreAction;
    public CommentDialogAdapter(Context context, List<UserCommentDTO> commentItemModelList, boolean isLoadMoreButtonVisible) {
        this.context = context;
        this.commentItemModelList = commentItemModelList;
        this.isLoadMoreButtonVisible = isLoadMoreButtonVisible;
    }

    public void setLoadMoreAction(View.OnClickListener loadMoreAction) {
        this.loadMoreAction = loadMoreAction;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == commentItemModelList.size()) {
            return VIEW_TYPE_LOAD_MORE;
        } else {
            return VIEW_TYPE_COMMENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_COMMENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            return new CommentDialogHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_COMMENT) {
            CommentDialogHolder commentHolder = (CommentDialogHolder) holder;
            UserCommentDTO cmtItem = this.commentItemModelList.get(position);

            if (cmtItem.getAvatar() != null) {
                byte[] avatarByteData = Base64.decode(cmtItem.getAvatar(), Base64.DEFAULT);
                commentHolder.imgCommentAvatar.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
            } else {
                commentHolder.imgCommentAvatar.setImageResource(R.drawable.ic_blank_avatar);
            }

            commentHolder.txtCommentUsrName.setText(cmtItem.getName());
            commentHolder.txtCommentContent.setText(cmtItem.getContent());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                commentHolder.txtCommentTime.setText(DateParser.timeSince(cmtItem.getCreateTime()));
            }

            commentHolder.txtCommentNoLiked.setText(String.valueOf(cmtItem.getLikeCommentCount()));

            commentHolder.btnCommentUnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentHolder.btnCommentUnLike.setVisibility(View.GONE);
                    commentHolder.btnCommentLiked.setVisibility(View.VISIBLE);
                }
            });
            commentHolder.btnCommentLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentHolder.btnCommentLiked.setVisibility(View.GONE);
                    commentHolder.btnCommentUnLike.setVisibility(View.VISIBLE);
                }
            });
        } else {
            LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
            loadMoreHolder.btnLoadMore.setOnClickListener(loadMoreAction);
        }
    }

    @Override
    public int getItemCount() {
        return isLoadMoreButtonVisible ? commentItemModelList.size() + 1 : commentItemModelList.size();
    }

    public static class CommentDialogHolder extends RecyclerView.ViewHolder {
        ImageView imgCommentAvatar;
        public TextView txtCommentUsrName, txtCommentContent, txtCommentTime, btnCommentReply, txtCommentNoLiked;
        ImageButton btnCommentUnLike, btnCommentLiked;

        public CommentDialogHolder(@NonNull View itemView) {
            super(itemView);
            imgCommentAvatar = itemView.findViewById(R.id.imgCommentAvatar);
            txtCommentUsrName = itemView.findViewById(R.id.txtCommentUsrName);
            txtCommentContent = itemView.findViewById(R.id.txtCommentContent);
            txtCommentTime = itemView.findViewById(R.id.txtCommentTime);
            btnCommentReply = itemView.findViewById(R.id.btnCommentReply);
            btnCommentUnLike = itemView.findViewById(R.id.btnCommentUnLike);
            btnCommentLiked = itemView.findViewById(R.id.btnCommentLiked);
            txtCommentNoLiked = itemView.findViewById(R.id.txtCommentNoLiked);
        }
    }

    public static class LoadMoreHolder extends RecyclerView.ViewHolder {
        Button btnLoadMore;
        public ProgressBar progressBar;

        public LoadMoreHolder(@NonNull View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
            progressBar = itemView.findViewById(R.id.processBarLoadMore);
        }
    }

}
