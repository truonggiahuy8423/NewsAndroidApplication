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

        if (bodyItem.getDataImage() == null) {
            holder.cvImgContent.setVisibility(View.GONE);
            holder.imgContentBI.setVisibility(View.GONE);
            holder.txtImgNameBI.setVisibility(View.GONE);
        }
        else{
            holder.cvImgContent.setVisibility(View.VISIBLE);
            holder.imgContentBI.setVisibility(View.VISIBLE);
            holder.cvImgContent.setVisibility(View.VISIBLE);
            byte[] imgContentData = Base64.decode(bodyItem.getDataImage(), Base64.DEFAULT);
            holder.imgContentBI.setImageBitmap(BitmapFactory.decodeByteArray(imgContentData, 0, imgContentData.length));
            holder.txtImgNameBI.setText(bodyItem.getImageName());
        }
    }

    @Override
    public int getItemCount() {
        return bodyItemModelList.size();
    }

    public class BodyItemScrollViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitleBI, txtContentBI, txtImgNameBI;
        private CardView cvImgContent;
        private ShapeableImageView imgContentBI;
        public BodyItemScrollViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleBI = itemView.findViewById(R.id.txtTitleBI);
            txtContentBI = itemView.findViewById(R.id.txtContentBI);
            txtImgNameBI = itemView.findViewById(R.id.txtImgNameBI);
            cvImgContent = itemView.findViewById(R.id.cvImgContent);
            imgContentBI = itemView.findViewById(R.id.imgContentBI);
        }
    }
}
