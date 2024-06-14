package com.example.newsandroidproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.BodyItemAdapter;
import com.example.newsandroidproject.adapter.ItemTouchHelperCallback;
import com.example.newsandroidproject.model.BodyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.recyclerview.widget.ItemTouchHelper;

public class PostArticleActivity extends AppCompatActivity implements BodyItemAdapter.OnItemLongClickListener {

    private Toolbar toolbar;
    private ImageButton btnCancel;
    private TextView btnPost;
    private ScrollView scrollView;
    private RecyclerView rvBodyItems;
    private BodyItemAdapter itemAdapter;
    private ImageButton btnAddItem;
    private ImageButton btnGoUp;
    private TextView txtUserName, txtFollower, tvThumbnail, textView3;
    private EditText edtTitle, edtDescription, edtThumbnailName;
    private TextInputLayout edtTitleLayout, edtDescriptionLayout, edtThumbnailNameLayout;
    private com.google.android.material.imageview.ShapeableImageView imgAvar, edtThumbnail;
    private LinearLayout linearLayout4;

    private List<BodyItem> items;
    private RecyclerView.ViewHolder currentViewHolder;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_FOR_ITEM = 2;

    // Trong phương thức setUpBodyItemRecycleViewAdapter của PostArticleActivity
//    private void setUpBodyItemRecycleViewAdapter() {
//        items = new ArrayList<>();
//        itemAdapter = new BodyItemAdapter(this, items);
//        itemAdapter.setImagePickerAction(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openImagePickerForItem();
//            }
//        });
//        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvBodyItems.setAdapter(itemAdapter);
//
//        // Thêm hỗ trợ cho kéo và thả vào RecyclerView
//        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
//                ItemTouchHelper.UP | ItemTouchHelper.DOWN, // Cho phép kéo lên và kéo xuống
//                0 // Không hỗ trợ kéo để xóa (0 là mặc định)
//        ) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                // Lấy vị trí của ViewHolder hiện tại và ViewHolder target
//                int fromPosition = viewHolder.getAdapterPosition();
//                int toPosition = target.getAdapterPosition();
//
//                // Hoán đổi vị trí của các phần tử trong danh sách và cập nhật Adapter
//                Collections.swap(items, fromPosition, toPosition);
//                itemAdapter.notifyItemMoved(fromPosition, toPosition);
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                // Xử lý hành động khi người dùng kéo để xóa (nếu cần)
//            }
//        };
//
//        // Gắn ItemTouchHelper vào RecyclerView
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(rvBodyItems);
//    }
    @Override
    public void onItemLongClick(int position) {
        // Hiển thị dialog xác nhận xóa phần tử
        new AlertDialog.Builder(this)
                .setTitle("Xóa phần tử")
                .setMessage("Bạn có muốn xóa phần tử này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Xóa phần tử khỏi danh sách
                    items.remove(position);
                    itemAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        // Find view by ID
        toolbar = findViewById(R.id.tool_bar);
        btnCancel = findViewById(R.id.btnCancel);
        btnPost = findViewById(R.id.btnPost);
        scrollView = findViewById(R.id.scrollView2);
        rvBodyItems = findViewById(R.id.rvBodyItems);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnGoUp = findViewById(R.id.btnGoUp);
        txtUserName = findViewById(R.id.txtUserName);
        txtFollower = findViewById(R.id.txtFollower);
        tvThumbnail = findViewById(R.id.tvThumbnail);
        textView3 = findViewById(R.id.textView3);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        edtThumbnailName = findViewById(R.id.edtThumbnailName);
        imgAvar = findViewById(R.id.imgAvar);
        edtThumbnail = findViewById(R.id.edtThumbnail);
        linearLayout4 = findViewById(R.id.linearLayout4);

        edtTitleLayout = findViewById(R.id.edtTitleLayout);
        edtDescriptionLayout = findViewById(R.id.edtDescriptionLayout);
        edtThumbnailNameLayout = findViewById(R.id.edtThumbnailNameLayout);

        setUpBodyItemRecycleViewAdapter();
        itemAdapter.setOnItemLongClickListener(this);


        // Setup any necessary listeners or initializations here
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAndShowDialog();
            }
        });

        edtThumbnail.setOnClickListener(view -> openImagePicker());

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Post btn clicked!", Toast.LENGTH_SHORT).show();

                if (validateInputs()) {
                    // Code to post the article goes here

                }
            }
        });



        edtTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !edtTitle.getText().toString().trim().isEmpty()) {
                edtTitleLayout.setBoxStrokeColor(getResources().getColor(R.color.primaryTextColor));
                edtTitleLayout.setBoxStrokeWidth(0);
                edtTitleLayout.setError(null);
                edtTitleLayout.setErrorEnabled(false);
            }
        });

        edtDescription.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !edtDescription.getText().toString().trim().isEmpty()) {
                edtDescriptionLayout.setBoxStrokeColor(getResources().getColor(R.color.primaryTextColor));
                edtDescriptionLayout.setBoxStrokeWidth(0);
                edtDescriptionLayout.setError(null);
                edtDescriptionLayout.setErrorEnabled(false);
            }
        });

        edtThumbnailName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !edtThumbnailName.getText().toString().trim().isEmpty()) {
                edtThumbnailNameLayout.setBoxStrokeColor(getResources().getColor(R.color.primaryTextColor));
                edtThumbnailNameLayout.setBoxStrokeWidth(0);
                edtThumbnailNameLayout.setError(null);
                edtDescriptionLayout.setErrorEnabled(false);
            }
        });

    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Convert 1dp to pixels
        float strokeWidthInPx = getResources().getDisplayMetrics().density;

        if (edtTitle.getText().toString().trim().isEmpty()) {
            edtTitleLayout.setBoxStrokeWidth((int) strokeWidthInPx);
            edtTitleLayout.setError("Không được để trống");
            isValid = false;
        } else {
            edtTitleLayout.setError(null); edtTitleLayout.setErrorEnabled(false);
        }

        if (edtDescription.getText().toString().trim().isEmpty()) {
            edtDescriptionLayout.setBoxStrokeWidth((int) strokeWidthInPx);
            edtDescriptionLayout.setError("Không được để trống");
            isValid = false;
        } else {
            edtDescriptionLayout.setError(null); edtDescriptionLayout.setErrorEnabled(false);
        }

        if (edtThumbnailName.getText().toString().trim().isEmpty()) {
            edtThumbnailNameLayout.setBoxStrokeWidth((int) strokeWidthInPx);
            edtThumbnailNameLayout.setError("Không được để trống");
            isValid = false;
        } else {
            edtThumbnailNameLayout.setError(null); edtThumbnailNameLayout.setErrorEnabled(false);
        }

        tvThumbnail.setTextColor(getResources().getColor(R.color.primaryTextColor));
        if ((edtThumbnail.getDrawable() != null && edtThumbnail.getDrawable().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.default_image).getConstantState()))) {
            isValid = false;
            tvThumbnail.setTextColor(getResources().getColor(R.color.red));
        }

        for (int i = 0; i<items.size();i++) {
            RecyclerView.ViewHolder holder = rvBodyItems.findViewHolderForAdapterPosition(i);
            switch (items.get(i).getItemType()) {
                case BodyItem.BODY_TITLE_ITEM:
                    BodyItemAdapter.BodyTitleViewHolder viewHolder = (BodyItemAdapter.BodyTitleViewHolder)holder;
                    if (viewHolder.edtBodyTitle.getText().toString().trim().isEmpty()) {
                        viewHolder.edtBodyTitleLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder.edtBodyTitleLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder.edtBodyTitleLayout.setError(null); viewHolder.edtBodyTitleLayout.setErrorEnabled(false);
                    }
                    break;
                case BodyItem.PARAGRAPH_ITEM:
                    BodyItemAdapter.ParagraphViewHolder viewHolder2 = (BodyItemAdapter.ParagraphViewHolder)holder;
                    if (viewHolder2.edtParagraph.getText().toString().trim().isEmpty()) {
                        viewHolder2.edtParagraphLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder2.edtParagraphLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder2.edtParagraphLayout.setError(null); viewHolder2.edtParagraphLayout.setErrorEnabled(false);
                    }
                    break;
                case BodyItem.IMAGE_ITEM:
                    BodyItemAdapter.ImageViewHolder viewHolder3 = (BodyItemAdapter.ImageViewHolder)holder;
                    if (viewHolder3.edtImageName.getText().toString().trim().isEmpty()) {
                        viewHolder3.edtImageNameLayout.setBoxStrokeWidth((int) strokeWidthInPx);
                        viewHolder3.edtImageNameLayout.setError("Không được để trống");
                        isValid = false;
                    } else {
                        viewHolder3.edtImageNameLayout.setError(null); viewHolder3.edtImageNameLayout.setErrorEnabled(false);
                    }
                    viewHolder3.tvImage.setTextColor(getResources().getColor(R.color.primaryTextColor));
                    if ((viewHolder3.edtImage.getDrawable() != null && viewHolder3.edtImage.getDrawable().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.default_image).getConstantState()))) {
                        viewHolder3.tvImage.setTextColor(getResources().getColor(R.color.red));
                        isValid = false;
                    }

                    break;
            }
        }
        return isValid;
    }


    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openImagePickerForItem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FOR_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                edtThumbnail.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST_FOR_ITEM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ((BodyItemAdapter.ImageViewHolder)currentViewHolder).edtImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setCurrentViewHolder(RecyclerView.ViewHolder viewHolder) {
        this.currentViewHolder = viewHolder;
    }

    public RecyclerView.ViewHolder getCurrentViewHolder() {
        return currentViewHolder;
    }

    private void setUpBodyItemRecycleViewAdapter() {
        items = new ArrayList<>();
        itemAdapter = new BodyItemAdapter(this, items);
        itemAdapter.setImagePickerAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePickerForItem();
            }
        });
        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBodyItems.setAdapter(itemAdapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(itemAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvBodyItems);
    }
//    private void setUpBodyItemRecycleViewAdapter() {
//        items = new ArrayList<>();
//        itemAdapter = new BodyItemAdapter(this, items);
//        itemAdapter.setImagePickerAction(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openImagePickerForItem();
//            }
//        });
//        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvBodyItems.setAdapter(itemAdapter);
//    }

    private void initAndShowDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_body_item_dialog);

        LinearLayout btnImage = dialog.findViewById(R.id.btnImage);
        LinearLayout btnBodyTitle = dialog.findViewById(R.id.btnTitle);
        LinearLayout btnParagraph = dialog.findViewById(R.id.btnContent);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Image button clicked", Toast.LENGTH_SHORT).show();
                btnImage.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.IMAGE_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                dialog.dismiss();
            }
        });

        btnBodyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Title button clicked", Toast.LENGTH_SHORT).show();
                btnBodyTitle.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.BODY_TITLE_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                dialog.dismiss();
            }
        });

        btnParagraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostArticleActivity.this, "Paragraph button clicked", Toast.LENGTH_SHORT).show();
                btnParagraph.setBackgroundColor(Color.parseColor("#FF888888"));
                items.add(new BodyItem(BodyItem.PARAGRAPH_ITEM));
                itemAdapter.notifyItemInserted(items.size() - 1);
                dialog.dismiss();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > Math.abs(velocityX)) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        dialog.findViewById(R.id.dialogRootView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        dialog.show();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        int minHeight = (int) (screenHeight * 3 / 5);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, minHeight);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
