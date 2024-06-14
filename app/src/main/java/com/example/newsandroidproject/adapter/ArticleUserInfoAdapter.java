package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.model.viewmodel.ArticleUserInfoDTO;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ArticleUserInfoAdapter extends RecyclerView.Adapter<ArticleUserInfoAdapter.ArticleUserInfoViewHolder> {
    Context context;
    List<ArticleUserInfoDTO> articleUserInfoDTOList;

    public ArticleUserInfoAdapter(Context context, List<ArticleUserInfoDTO> articleUserInfoDTOList) {
        this.context = context;
        this.articleUserInfoDTOList = articleUserInfoDTOList;
    }

    @Override
    public ArticleUserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_userinfo_article, parent, false);
        return new ArticleUserInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleUserInfoViewHolder holder, int position) {
        ArticleUserInfoDTO article = articleUserInfoDTOList.get(position);

        holder.txtTitleArtiUserInfo.setText(article.getTitle());
        holder.txtNoViewedArtiUserInfo.setText(String.valueOf(article.getViewCount()));

        // TODO: Set date
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        String formattedDate = formatter.format(article.getCreateTime());
        holder.txtDateArtiUserInfo.setText(formattedDate);

        // TODO: Set images
        if (article.getThumbnail() == null) {
            holder.ivThumbnailUserInfo.setVisibility(View.GONE);
            holder.cvThumbnailUserInfo.setVisibility(View.GONE);
        }
        else{
            holder.ivThumbnailUserInfo.setVisibility(View.VISIBLE);
            holder.cvThumbnailUserInfo.setVisibility(View.VISIBLE);
            byte[] thumbnailByteData = Base64.decode(article.getThumbnail(), Base64.DEFAULT);
            holder.ivThumbnailUserInfo.setImageBitmap(BitmapFactory.decodeByteArray(thumbnailByteData, 0, thumbnailByteData.length));
        }
    }

    @Override
    public int getItemCount() {
        return articleUserInfoDTOList.size();
    }

    public class ArticleUserInfoViewHolder extends RecyclerView.ViewHolder{
        private CardView cvThumbnailUserInfo;
        private ShapeableImageView ivThumbnailUserInfo;
        private TextView txtTitleArtiUserInfo, txtDateArtiUserInfo, txtNoViewedArtiUserInfo;
        public ArticleUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            cvThumbnailUserInfo = itemView.findViewById(R.id.cvThumbnailUserInfo);
            ivThumbnailUserInfo = itemView.findViewById(R.id.ivThumbnailUserInfo);
            txtTitleArtiUserInfo = itemView.findViewById(R.id.txtTitleArtiUserInfo);
            txtDateArtiUserInfo = itemView.findViewById(R.id.txtDateArtiUserInfo);
            txtNoViewedArtiUserInfo = itemView.findViewById(R.id.txtNoViewedArtiUserInfo);
        }
    }
}
