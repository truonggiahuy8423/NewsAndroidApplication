package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;

import java.util.List;

public class CommentDialogAdapter extends RecyclerView.Adapter<CommentDialogAdapter.CommentDialogHolder> {
    private Context context;
    private List<UserCommentDTO> commentItemModelList;

    public CommentDialogAdapter(Context context, List<UserCommentDTO> commentItemModelList) {
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
        UserCommentDTO cmtItem = this.commentItemModelList.get(position);

        if (cmtItem.getAvatar() != null) {
            byte[] avatarByteData = Base64.decode(cmtItem.getAvatar(), Base64.DEFAULT);
            holder.imgCommentAvatar.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
        } else {
            holder.imgCommentAvatar.setImageResource(R.drawable.ic_blank_avatar);
        }


        holder.txtCommentUsrName.setText(cmtItem.getName());

        holder.txtCommentContent.setText(cmtItem.getContent());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.txtCommentTime.setText(DateParser.timeSince(cmtItem.getCreateTime()));
        }

        holder.txtCommentNoLiked.setText(String.valueOf(cmtItem.getLikeCommentCount()));

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

    public static class CommentDialogHolder extends RecyclerView.ViewHolder{
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
