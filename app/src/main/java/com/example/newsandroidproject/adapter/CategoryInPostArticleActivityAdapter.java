package com.example.newsandroidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.common.AnimationUtil;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Category;

import java.util.List;

public class CategoryInPostArticleActivityAdapter extends RecyclerView.Adapter<ChosenCategoryInDialogAdapter.ChosenCategoryInDialogViewHolder>{
    private Context context;
    private List<Category> categoryList;





    public CategoryInPostArticleActivityAdapter(Context context, UniqueList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ChosenCategoryInDialogAdapter.ChosenCategoryInDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_cate_remove_btn, parent, false);
        return new ChosenCategoryInDialogAdapter.ChosenCategoryInDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenCategoryInDialogAdapter.ChosenCategoryInDialogViewHolder holder, int position) {
        // Bind data to the item
        Category category = categoryList.get(position);
        holder.categoryButton.setText(category.getName());
        AnimationUtil.animate(holder.itemView, position);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                categoryList.remove(position);
                CategoryInPostArticleActivityAdapter.this.notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ChosenCategoryInDialogViewHolder extends RecyclerView.ViewHolder {
        Button categoryButton;
        public ImageButton remove;
        public ChosenCategoryInDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryButton = itemView.findViewById(R.id.btn_cate1);
            remove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
