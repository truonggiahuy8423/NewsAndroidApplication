package com.example.newsandroidproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Category;

import java.util.List;

public class CategoryInDialogAdapter extends RecyclerView.Adapter<CategoryInDialogAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private List<Category> chosen;

    ChosenCategoryInDialogAdapter chosenAdapter;

    public ChosenCategoryInDialogAdapter getChosenAdapter() {
        return chosenAdapter;
    }

    public void setChosenAdapter(ChosenCategoryInDialogAdapter chosenAdapter) {
        this.chosenAdapter = chosenAdapter;
    }

    public CategoryInDialogAdapter(Context context, UniqueList<Category> categoryList, UniqueList<Category> chosen) {
        this.context = context;
        this.categoryList = categoryList;
        this.chosen = chosen;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Bind data to the item
        Category category = categoryList.get(position);
        holder.categoryButton.setText(category.getName());

        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                chosen.add(categoryList.get(position));
                chosenAdapter.notifyItemInserted(chosen.size() - 1);
                categoryList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button categoryButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryButton = itemView.findViewById(R.id.btn_cate1);
        }
    }
}
