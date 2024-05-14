package com.example.newsandroidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;

import java.util.ArrayList;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.ViewHolder> {
    private final ArrayList<String> mData;
    private final LayoutInflater mInflater;
    private Context context;

    public CategoryRecycleViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }
    private int selectedPosition = 0; // Vị trí của item hiện đang được nhấn

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.textViewItem.setText(item);

        // Thay đổi màu chữ cho item
        if (position == selectedPosition) {
            Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.category_item_fade_in);
            // Nếu là item được nhấn, thay đổi màu chữ thành đỏ
            holder.itemView.startAnimation(fadeIn);
            holder.textViewItem.setTextColor(context.getResources().getColor(R.color.primaryThemeColor));
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.category_item_bg));

        } else {
//            Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.category_item_fade_out);
            // Nếu không phải item được nhấn, giữ nguyên màu chữ
            holder.textViewItem.setTextColor(context.getResources().getColor(R.color.primaryTextColor));
            holder.itemView.setBackground(null); // hoặc set background là background mặc định của item

        }

        // Đặt sự kiện click listener cho item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật vị trí của item được nhấn
                selectedPosition = holder.getAdapterPosition();
                // Thông báo cho adapter biết rằng dữ liệu đã thay đổi
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItem;

        ViewHolder(View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.category_item);
        }
    }
}
