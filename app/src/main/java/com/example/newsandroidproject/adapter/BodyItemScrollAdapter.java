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
import com.example.newsandroidproject.model.viewmodel.BodyItemModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class BodyItemScrollAdapter extends RecyclerView.Adapter<BodyItemScrollAdapter.BodyItemScrollViewHolder> {
    private Context context;
    private List<BodyItemModel> bodyItemModelList;

    public BodyItemScrollAdapter(Context context, List<BodyItemModel> bodyItemModelList) {
        this.context = context;
        this.bodyItemModelList = bodyItemModelList;
    }

    @NonNull
    @Override
    public BodyItemScrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BodyItemScrollViewHolder(LayoutInflater.from(context).inflate(R.layout.item_scroll_bodyitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BodyItemScrollViewHolder holder, int position) {
        BodyItemModel bodyItem = bodyItemModelList.get(position);

        if(bodyItem.getBodyTitle() == null){
            holder.txtTitleBI.setVisibility(View.GONE);
        }else{
            holder.txtTitleBI.setText(bodyItem.getBodyTitle());
        }

        if(bodyItem.getContent() == null){
            holder.txtContentBI.setVisibility(View.GONE);
        }else{
            holder.txtContentBI.setText(bodyItem.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return bodyItemModelList.size();
    }

    public class BodyItemScrollViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitleBI, txtContentBI;
        public BodyItemScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleBI = itemView.findViewById(R.id.txtTitleBI);
            txtContentBI = itemView.findViewById(R.id.txtContentBI);
        }
    }
}
