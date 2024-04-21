package com.example.newsandroidproject;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.Model.CommentItemModel;
import com.example.newsandroidproject.Model.NewsContentModel;
import com.example.newsandroidproject.RecyclerViewAdapter.CommentDialogAdapter;
import com.example.newsandroidproject.RecyclerViewAdapter.NewsContentAdapter;
import com.example.newsandroidproject.RecyclerViewAdapter.SpecialNewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton btnBack;
    ImageView imgAvar;
    TextView txtUserName, txtFollower, txtDate, txtNoSaved, txtNoViewed, txtNoCommented;
    Button btn_cate1, btn_cate2, btn_cate3, btn_more;
    RecyclerView rvContent, rvSpNews;
    List<NewsContentModel> newsContentModelList, spNewsList;
    NewsContentAdapter newsContentAdapter;
    SpecialNewsAdapter specialNewsAdapter;
    SeekBar sbFontSize;
    ImageButton btnFontFamily, btnComment, btnHistory, btnBookMark, btnBookMarkSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reading);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadTopToolBar();
        loadAuthor();
        loadCategories();
        loadContent();
        loadSpNews();
        loadBottomToolBar();
    }

    private void loadTopToolBar() {
        toolbar = findViewById(R.id.topToolBarReadingPage);
        btnBack = findViewById(R.id.btnBack);
        setSupportActionBar(toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_toolbar_menu_reading_page, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnMenu){
            Toast.makeText(this, "Turn on menu",Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.btnBack){
            finish();
        }
        return true;
    }
    private void loadAuthor() {
        imgAvar = findViewById(R.id.imgAvar);
        txtUserName = findViewById(R.id.txtUserName);
        txtFollower = findViewById(R.id.txtFollower);
        txtDate = findViewById(R.id.txtDate);
        txtNoSaved = findViewById(R.id.txtNoSaved);
        txtNoViewed = findViewById(R.id.txtNoViewed);
        txtNoCommented = findViewById(R.id.txtNoCommented);

        imgAvar.setImageResource(R.drawable.ic_blank_avatar);
        txtUserName.setText("Nguyen Duy Khanh");
        txtFollower.setText(shortenNumber(108000) + " người theo dõi");
        txtDate.setText("Thứ năm, 16/10/2024 18:20");
        txtNoSaved.setText(shortenNumber(100));
        txtNoViewed.setText(shortenNumber(2000));
        txtNoCommented.setText(shortenNumber(2300));
    }
    private String shortenNumber(int n){
        String sn = "";
        if(n < 10000){
            sn = String.valueOf(n);
        }
        else if(n >= 10000){
            sn = String.valueOf((float)n/1000) + "N";
        }else if(n >= 1000000){
            sn = String.valueOf((float)n/1000000) + "Tr";
        }
        else if(n >= 1000000000){
            sn = String.valueOf((float)n/1000000000) + "T";
        }
        return sn;
    }
    private void loadCategories() {
        btn_cate1 = findViewById(R.id.btn_cate1);
        btn_cate2 = findViewById(R.id.btn_cate2);
        btn_cate3 = findViewById(R.id.btn_cate3);

        btn_cate1.setText("Pháp luật");
        btn_cate2.setText("Thời sự");
    }
    private void loadContent() {
        rvContent = findViewById(R.id.rvContent);
        rvContent.setHasFixedSize(true);
        rvContent.setLayoutManager(new GridLayoutManager(this, 1));

        newsContentModelList = new ArrayList<>();
        newsContentModelList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                "Theo diễn biến...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentModelList.add(new NewsContentModel(null,
                "Tình tiết giảm nhẹ không đủ khoan hồng",
                "Luận vội, Viện kiểm sát đánh giá bị cáo...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentModelList.add(new NewsContentModel(null,
                "Gần 1 tháng TAND...",
                "Theo diễn biến...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentAdapter = new NewsContentAdapter(this, newsContentModelList);
        rvContent.setAdapter(newsContentAdapter);
    }
    private void loadSpNews() {
        rvSpNews = findViewById(R.id.rvSpNews);
        rvSpNews.setHasFixedSize(true);
        rvSpNews.setLayoutManager(new GridLayoutManager(this, 1));
        spNewsList = new ArrayList<>();
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.rack));
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.rack));
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.rack));
        specialNewsAdapter = new SpecialNewsAdapter(this, spNewsList);
        rvSpNews.setAdapter(specialNewsAdapter);
    }
    private void loadBottomToolBar() {
        sbFontSize = findViewById(R.id.sbFontSize);
        btnFontFamily = findViewById(R.id.btnFontFamily);
        btnComment = findViewById(R.id.btnComment);
        btnHistory = findViewById(R.id.btnHistory);
        btnBookMark = findViewById(R.id.btnBookmark);
        btnBookMarkSaved = findViewById(R.id.btnBookmarkSaved);
        sbFontSize.setProgress(16);
        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                newsContentAdapter.setTextSize(progress);
                specialNewsAdapter.setTextSize(progress);
                newsContentAdapter.notifyDataSetChanged();
                specialNewsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBookMark.setVisibility(View.GONE);
                btnBookMarkSaved.setVisibility(View.VISIBLE);
            }
        });
        btnBookMarkSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBookMarkSaved.setVisibility(View.GONE);
                btnBookMark.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comment);

        TextView txtNoCommentedOfNews = dialog.findViewById(R.id.txtNoCommentedOfNews);
        RecyclerView rvCommentList = dialog.findViewById(R.id.rvCommentList);
        CommentDialogAdapter commentDialogAdapter;
        EditText edtCommentDialog = dialog.findViewById(R.id.edtCommentDialog);
        ImageButton btnSent = dialog.findViewById(R.id.btnSent);
        List<CommentItemModel> commentItemModelList = new ArrayList<>();
        txtNoCommentedOfNews.setText(String.valueOf(5) + " bình luận");
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        LinearLayout llComment = dialog.findViewById(R.id.llComment);


        rvCommentList.setHasFixedSize(true);
        rvCommentList.setLayoutManager(new GridLayoutManager(this, 1));
        commentItemModelList.add(new CommentItemModel(
                R.drawable.ic_blank_avatar,
                "Nguyen Van A",
                "Aduvjp",
                "1 giờ trước",
                100));
        commentItemModelList.add(new CommentItemModel(
                R.drawable.ic_blank_avatar,
                "Nguyen Van ABC",
                "Bruh",
                "3 giờ trước",
                1000));
        commentItemModelList.add(new CommentItemModel(
                R.drawable.ic_blank_avatar,
                "Nguyen Van EDF",
                "Lmao",
                "3 giờ trước",
                1314));
        commentItemModelList.add(new CommentItemModel(
                R.drawable.ic_blank_avatar,
                "Kaito",
                "Lmao",
                "5 giờ trước",
                1820));
        commentItemModelList.add(new CommentItemModel(
                R.drawable.ic_blank_avatar,
                "Meow meow",
                "Lmao",
                "3 giờ trước",
                3216));
        commentDialogAdapter = new CommentDialogAdapter(this, commentItemModelList);
        rvCommentList.setAdapter(commentDialogAdapter);


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



}