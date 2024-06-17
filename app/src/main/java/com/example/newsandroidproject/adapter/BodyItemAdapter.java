package com.example.newsandroidproject.adapter;

import android.annotation.SuppressLint;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.Category;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BodyItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<BodyItem> items;
    public PostArticleActivity context;
    public View.OnClickListener action;
    public View.OnClickListener action2;


    public View.OnTouchListener touchListener;

    public View.OnTouchListener getTouchListener() {
        return touchListener;
    }

    public void setTouchListener(View.OnTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public void setImagePickerAction(View.OnClickListener action) {
        this.action = action;
    }

    public void setImagePickerAction2(View.OnClickListener action) {
        this.action2 = action;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
    public void removedItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        BodyItem item = items.get(fromPosition);
        items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);}
    public OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public BodyItemAdapter(PostArticleActivity context, List<BodyItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BodyItem.HEADER;
        }
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
        } else if (viewType == BodyItem.HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Handle binding data to the ViewHolder if necessary
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;

            viewHolder.edtTitle.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !viewHolder.edtTitle.getText().toString().trim().isEmpty()) {
                    viewHolder.edtTitleLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    viewHolder.edtTitleLayout.setBoxStrokeWidth(0);
                    viewHolder.edtTitleLayout.setError(null);
                    viewHolder.edtTitleLayout.setErrorEnabled(false);
                }
            });

            viewHolder.edtDescription.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !viewHolder.edtDescription.getText().toString().trim().isEmpty()) {
                    viewHolder.edtDescriptionLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    viewHolder.edtDescriptionLayout.setBoxStrokeWidth(0);
                    viewHolder.edtDescriptionLayout.setError(null);
                    viewHolder.edtDescriptionLayout.setErrorEnabled(false);
                }
            });

            viewHolder.edtThumbnailName.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !viewHolder.edtThumbnailName.getText().toString().trim().isEmpty()) {
                    viewHolder.edtThumbnailNameLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    viewHolder.edtThumbnailNameLayout.setBoxStrokeWidth(0);
                    viewHolder.edtThumbnailNameLayout.setError(null);
                    viewHolder.edtDescriptionLayout.setErrorEnabled(false);
                }
            });

//            ((HeaderViewHolder)holder).edtThumbnail.setOnLongClickListener(v -> {
//                if (longClickListener != null) {
//                    longClickListener.onItemLongClick(position);
//                }
//                return true;
//            });


            
            

        } else if (holder instanceof BodyTitleViewHolder) {
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
                if (!hasFocus && !imageViewHolder.edtImageName.getText().toString().isEmpty()) {
                    imageViewHolder.edtImageNameLayout.setBoxStrokeColor(context.getResources().getColor(R.color.primaryTextColor));
                    imageViewHolder.edtImageNameLayout.setBoxStrokeWidth(0);
                    imageViewHolder.edtImageNameLayout.setError(null);
                    imageViewHolder.edtImageNameLayout.setErrorEnabled(false);
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

    public static class BodyTitleViewHolder extends RecyclerView.ViewHolder {
        public TextInputLayout edtBodyTitleLayout;
        public EditText edtBodyTitle;

        public BodyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            edtBodyTitleLayout = itemView.findViewById(R.id.edtBodyTitleLayout);
            edtBodyTitle = itemView.findViewById(R.id.edtBodyTitle);
        }
    }

    public static class ParagraphViewHolder extends RecyclerView.ViewHolder {
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

    private View.OnClickListener action3;
    public void setOnclickForAddCateBtn(View.OnClickListener a) {
        action3 = a;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public Toolbar toolbar;
        public TextView txtUserName, txtFollower, tvThumbnail, textView3;
        public EditText edtTitle, edtDescription, edtThumbnailName;
        public TextInputLayout edtTitleLayout, edtDescriptionLayout, edtThumbnailNameLayout;
        public com.google.android.material.imageview.ShapeableImageView imgAvar, edtThumbnail;
        public LinearLayout linearLayout4;

        public RecyclerView rvCate;

        public CategoryInPostArticleActivityAdapter adapterCate;

        public UniqueList<Category> cates;
        public ImageButton btnAddCate;

        @SuppressLint("NotifyDataSetChanged")
        public void setCates(UniqueList<Category> input) {
            this.cates.clear();
            this.cates.addAll(input);
            adapterCate.notifyDataSetChanged();
        }

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtFollower = itemView.findViewById(R.id.txtFollower);
            tvThumbnail = itemView.findViewById(R.id.tvThumbnail);
            textView3 = itemView.findViewById(R.id.textView3);
            edtTitle = itemView.findViewById(R.id.edtTitle);
            edtDescription = itemView.findViewById(R.id.edtDescription);
            edtThumbnailName = itemView.findViewById(R.id.edtThumbnailName);
            imgAvar = itemView.findViewById(R.id.imgAvar);
            edtThumbnail = itemView.findViewById(R.id.edtThumbnail);
            linearLayout4 = itemView.findViewById(R.id.linearLayout4);

            edtTitleLayout = itemView.findViewById(R.id.edtTitleLayout);
            edtDescriptionLayout = itemView.findViewById(R.id.edtDescriptionLayout);
            edtThumbnailNameLayout = itemView.findViewById(R.id.edtThumbnailNameLayout);

            rvCate = itemView.findViewById(R.id.rvCategories);
            btnAddCate = itemView.findViewById(R.id.btnAddCate);
            cates = new UniqueList<>();

            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

            rvCate.setLayoutManager(flexboxLayoutManager);

            // Set the adapter
            adapterCate = new CategoryInPostArticleActivityAdapter(context, cates);
            rvCate.setAdapter(adapterCate);




            btnAddCate.setOnClickListener(action3);

            edtThumbnail.setOnClickListener(view -> {
                context.setCurrentViewHolder(this);
                action2.onClick(view);
            });
//            edtImage.setOnClickListener(view -> {
//                context.setCurrentViewHolder(this);
//                action.onClick(view);
//            });
        }
    }
}
