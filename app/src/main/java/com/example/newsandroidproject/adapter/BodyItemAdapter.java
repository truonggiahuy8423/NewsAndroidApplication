package com.example.newsandroidproject.adapter;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.model.BodyItem;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BodyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BodyItem> items;
    private PostArticleActivity context;

    public BodyItemAdapter() {
    }

    public BodyItemAdapter(PostArticleActivity context, List<BodyItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BodyItem.BODY_TITLE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_body_title, parent, false);
            return new HeaderItemViewHolder(view);
        } else if (viewType == BodyItem.PARAGRAPH_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
            return new HeaderItemViewHolder(view);
        } else if (viewType == BodyItem.IMAGE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new HeaderItemViewHolder(view);

        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class HeaderItemViewHolder extends RecyclerView.ViewHolder {
//        public Button btnLoadMore;
//        public ProgressBar progressBar;

//        TextInputLayout edtLayout;

        public HeaderItemViewHolder(@NonNull View itemView) {
            super(itemView);
////            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
//            edtLayout = itemView.findViewById(R.id.op);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                edtLayout.setCursorColor(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColor)));
//            }
        }
    }
}
