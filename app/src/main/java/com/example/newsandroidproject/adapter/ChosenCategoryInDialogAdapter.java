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
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Category;

import java.util.List;

public class ChosenCategoryInDialogAdapter extends RecyclerView.Adapter<ChosenCategoryInDialogAdapter.ChosenCategoryInDialogViewHolder>{
    private Context context;
    private List<Category> categoryList;
    CategoryInDialogAdapter cateAdapter;



    public CategoryInDialogAdapter getCateAdapter() {
        return cateAdapter;
    }

    public void setCateAdapter(CategoryInDialogAdapter cateAdapter) {
        this.cateAdapter = cateAdapter;
    }

    private List<Category> cate;

    public ChosenCategoryInDialogAdapter(Context context, UniqueList<Category> categoryList, UniqueList<Category> cate) {
        this.context = context;
        this.categoryList = categoryList;
        this.cate = cate;
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
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                cate.add(categoryList.get(position));
                System.out.println(cate.size());
                cateAdapter.notifyDataSetChanged();
                categoryList.remove(position);
                notifyItemRemoved(position);
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
