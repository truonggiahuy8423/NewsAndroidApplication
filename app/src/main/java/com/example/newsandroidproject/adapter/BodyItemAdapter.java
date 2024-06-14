package com.example.newsandroidproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.model.BodyItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BodyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BodyItem> items;
    private PostArticleActivity context;
    private View.OnClickListener action;

    public void setImagePickerAction(View.OnClickListener action) {
        this.action = action;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        BodyItem item = items.get(fromPosition);
        items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);}
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
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
            return new BodyTitleViewHolder(view);
        } else if (viewType == BodyItem.PARAGRAPH_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
            return new ParagraphViewHolder(view);
        } else if (viewType == BodyItem.IMAGE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Handle binding data to the ViewHolder if necessary

        if (holder instanceof BodyTitleViewHolder) {
            BodyTitleViewHolder bodyTitleViewHolder = (BodyTitleViewHolder) holder;
            bodyTitleViewHolder.edtBodyTitle.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !bodyTitleViewHolder.edtBodyTitle.getText().toString().isEmpty()) {
                    bodyTitleViewHolder.edtBodyTitleLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    bodyTitleViewHolder.edtBodyTitleLayout.setBoxStrokeWidth(0);
                    bodyTitleViewHolder.edtBodyTitleLayout.setError(null);
                    bodyTitleViewHolder.edtBodyTitleLayout.setErrorEnabled(false);
                }

            });
            ((BodyTitleViewHolder)holder).edtBodyTitle.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(position);
                }
                return true;
            });
        } else if (holder instanceof ParagraphViewHolder) {
            ParagraphViewHolder paragraphViewHolder = (ParagraphViewHolder) holder;
            paragraphViewHolder.edtParagraph.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !paragraphViewHolder.edtParagraph.getText().toString().isEmpty()) {
                    paragraphViewHolder.edtParagraphLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    paragraphViewHolder.edtParagraphLayout.setBoxStrokeWidth(0);
                    paragraphViewHolder.edtParagraphLayout.setError(null);
                    paragraphViewHolder.edtParagraphLayout.setErrorEnabled(false);
                }
            });
            ((ParagraphViewHolder)holder).edtParagraph.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(position);
                }
                return true;
            });
        } else if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.edtImageName.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    imageViewHolder.edtImageNameLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                }
            });
            ((ImageViewHolder)holder).edtImage.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(position);
                }
                return true;
            });
            ((ImageViewHolder)holder).edtImageName.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(position);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class BodyTitleViewHolder extends RecyclerView.ViewHolder {
        public TextInputLayout edtBodyTitleLayout;
        public EditText edtBodyTitle;

        public BodyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            edtBodyTitleLayout = itemView.findViewById(R.id.edtBodyTitleLayout);
            edtBodyTitle = itemView.findViewById(R.id.edtBodyTitle);
        }
    }

    public class ParagraphViewHolder extends RecyclerView.ViewHolder {
        public TextInputLayout edtParagraphLayout;
        public EditText edtParagraph;

        public ParagraphViewHolder(@NonNull View itemView) {
            super(itemView);
            edtParagraphLayout = itemView.findViewById(R.id.edtParagraphLayout);
            edtParagraph = itemView.findViewById(R.id.edtParagraph);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView edtImage;
        public TextInputLayout edtImageNameLayout;
        public TextView tvImage;
        public EditText edtImageName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            edtImage = itemView.findViewById(R.id.edtImage);
            edtImageNameLayout = itemView.findViewById(R.id.edtImageNameLayout);
            edtImageName = itemView.findViewById(R.id.edtImageName);
            tvImage = itemView.findViewById(R.id.tvImage);

            edtImage.setOnClickListener(view -> {
                context.setCurrentViewHolder(this);
                action.onClick(view);
            });
        }
    }
}
