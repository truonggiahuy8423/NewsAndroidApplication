package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.model.viewmodel.CommentItemModel;
import com.example.newsandroidproject.R;

import java.util.List;

public class CommentDialogAdapter extends RecyclerView.Adapter<CommentDialogAdapter.CommentDialogHolder> {
    private Context context;
    private List<CommentItemModel> commentItemModelList;

    public CommentDialogAdapter(Context context, List<CommentItemModel> commentItemModelList) {
        this.context = context;
        this.commentItemModelList = commentItemModelList;
    }

    @NonNull
    @Override
    public CommentDialogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentDialogHolder holder, int position) {
        CommentItemModel cmtItem = this.commentItemModelList.get(position);
        holder.imgCommentAvatar.setImageResource(cmtItem.getCmtAvatar());
        holder.txtCommentUsrName.setText(cmtItem.getCmtUsrName());
        holder.txtCommentContent.setText(cmtItem.getCmtContent());
        holder.txtCommentTime.setText(cmtItem.getCmtTime());
        holder.txtCommentNoLiked.setText(String.valueOf(cmtItem.getCmtNoLiked()));
        holder.btnCommentUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnCommentUnLike.setVisibility(View.GONE);
                holder.btnCommentLiked.setVisibility(View.VISIBLE);
            }
        });
        holder.btnCommentLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnCommentLiked.setVisibility(View.GONE);
                holder.btnCommentUnLike.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentItemModelList.size();
    }

    public class CommentDialogHolder extends RecyclerView.ViewHolder{
        ImageView imgCommentAvatar;
        TextView txtCommentUsrName, txtCommentContent, txtCommentTime, btnCommentReply, txtCommentNoLiked;
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
}
