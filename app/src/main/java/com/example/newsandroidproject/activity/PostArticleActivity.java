package com.example.newsandroidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.ArticleRecycleViewAdapter;
import com.example.newsandroidproject.adapter.BodyItemAdapter;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.BodyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PostArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton btnCancel;
    private TextView btnPost;
    private RecyclerView rvBodyItems;
    private BodyItemAdapter itemAdapter;
    private ImageButton btnAddItem;

    private List<BodyItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        // Find view by ID
        toolbar = findViewById(R.id.tool_bar);
        btnCancel = findViewById(R.id.btnCancel);
        btnPost = findViewById(R.id.btnPost);
        rvBodyItems = findViewById(R.id.rvBodyItems);
        btnAddItem = findViewById(R.id.btnAddItem);

        setUpBodyItemRecycleViewAdapter();

        // Setup any necessary listeners or initializations here
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAndShowDialog();
            }
        });

    }

    private void setUpBodyItemRecycleViewAdapter() {
        items = new ArrayList<>();
        itemAdapter = new BodyItemAdapter(this, items);
        rvBodyItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBodyItems.setAdapter(itemAdapter);
    }

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
                // Hiển thị Toast
                Toast.makeText(PostArticleActivity.this, "Image button clicked", Toast.LENGTH_SHORT).show();
                // Đổi màu nền của LinearLayout
                btnImage.setBackgroundColor(Color.parseColor("#FF888888")); // Màu đỏ
                items.add(new BodyItem(BodyItem.IMAGE_ITEM));
                itemAdapter.notifyItemInserted(items.size()-1);
                dialog.dismiss();
            }
        });

        btnBodyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Hiển thị Toast
                Toast.makeText(PostArticleActivity.this, "Image button clicked", Toast.LENGTH_SHORT).show();
                // Đổi màu nền của LinearLayout
                btnImage.setBackgroundColor(Color.parseColor("#FF888888")); // Màu đỏ
                items.add(new BodyItem(BodyItem.BODY_TITLE_ITEM));
                itemAdapter.notifyItemInserted(items.size()-1);
                dialog.dismiss();
            }
        });

        btnParagraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Hiển thị Toast
                Toast.makeText(PostArticleActivity.this, "Image button clicked", Toast.LENGTH_SHORT).show();
                // Đổi màu nền của LinearLayout
                btnImage.setBackgroundColor(Color.parseColor("#FF888888")); // Màu đỏ
                items.add(new BodyItem(BodyItem.PARAGRAPH_ITEM));
                itemAdapter.notifyItemInserted(items.size()-1);
                dialog.dismiss();
            }
        });
        // GestureDetector to detect swipe down gesture
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
        // Lấy chiều cao của màn hình
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // Tính chiều cao tối thiểu của dialog (2/3 chiều cao màn hình)
        int minHeight = (int) (screenHeight * 3 / 5);

        // Đặt chiều cao của dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, minHeight);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}