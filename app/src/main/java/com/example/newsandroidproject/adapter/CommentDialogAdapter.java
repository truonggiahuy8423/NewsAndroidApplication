package com.example.newsandroidproject.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.activity.ReadingActivity;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.dto.CommentPostingRequest;
import com.example.newsandroidproject.model.dto.LikeCommentDTO;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    UserCommentDTO cmtItem = commentItemModelList.get(position);
                    commentHolder.btnCommentUnLike.setVisibility(View.GONE);
                    commentHolder.btnCommentLiked.setVisibility(View.VISIBLE);
                    commentHolder.txtCommentNoLiked.setText(String.valueOf(Integer.valueOf(cmtItem.getLikeCommentCount().toString()) + 1));

                    LikeCommentDTO likeCommentDTO = new LikeCommentDTO();
                    likeCommentDTO.setCommentId(cmtItem.getCommentId());
                    likeCommentDTO.setTime(DateParser.formatToISO8601(new Date()));
                    ArticleApi apiService = RetrofitService.getClient(context).create(ArticleApi.class);
                    Call<LikeCommentDTO> call = apiService.unlikeComment(likeCommentDTO);
                    call.enqueue(new Callback<LikeCommentDTO>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<LikeCommentDTO> call, Response<LikeCommentDTO> response) {
                            // Ẩn ProgressBar và hiển thị lại nút gửi
                            if (response.code() == 200 && response.body() != null) {

                            } else {
                                try {
                                    ResponseException errorResponse = JsonParser.parseError(response);
                                    Toast.makeText(context, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeCommentDTO> call, Throwable t) {

                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            commentHolder.btnCommentLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserCommentDTO cmtItem = commentItemModelList.get(position);
                    commentHolder.btnCommentLiked.setVisibility(View.GONE);
                    commentHolder.btnCommentUnLike.setVisibility(View.VISIBLE);
                    commentHolder.txtCommentNoLiked.setText(String.valueOf(Integer.valueOf(cmtItem.getLikeCommentCount().toString()) - 1));

                    LikeCommentDTO likeCommentDTO = new LikeCommentDTO();
                    likeCommentDTO.setCommentId(cmtItem.getCommentId());
                    likeCommentDTO.setTime(DateParser.formatToISO8601(new Date()));
                    ArticleApi apiService = RetrofitService.getClient(context).create(ArticleApi.class);
                    Call<LikeCommentDTO> call = apiService.likeComment(likeCommentDTO);
                    call.enqueue(new Callback<LikeCommentDTO>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<LikeCommentDTO> call, Response<LikeCommentDTO> response) {
                            // Ẩn ProgressBar và hiển thị lại nút gửi
                            if (response.code() == 200 && response.body() != null) {

                            } else {
                                try {
                                    ResponseException errorResponse = JsonParser.parseError(response);
                                    Toast.makeText(context, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LikeCommentDTO> call, Throwable t) {

                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        public Button btnLoadMore;
        public ProgressBar progressBar;

        public LoadMoreHolder(@NonNull View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
            progressBar = itemView.findViewById(R.id.processBarLoadMore);
        }
    }

}
