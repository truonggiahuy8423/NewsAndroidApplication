package com.example.newsandroidproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.Model.NewsContent;
import com.example.newsandroidproject.RecyclerViewAdapter.NewsContentAdapter;
import com.example.newsandroidproject.RecyclerViewAdapter.SpecialNewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton btnBack;
    ImageView imgAvar;
    TextView txtUserName, txtFollower, txtDate, txtNoSaved, txtNoViewed, txtNoCommented;
    Button btn_cate1, btn_cate2, btn_cate3;
    RecyclerView rvContent, rvSpNews;
    List<NewsContent> newsContentList, spNewsList;
    NewsContentAdapter newsContentAdapter;
    SpecialNewsAdapter specialNewsAdapter;
    SeekBar sbFontSize;
    ImageButton btnFontFamily, btnComment, btnHistory, btnBookMark;

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

    private void loadContent() {
        rvContent = findViewById(R.id.rvContent);
        rvContent.setHasFixedSize(true);
        rvContent.setLayoutManager(new GridLayoutManager(this, 1));

        newsContentList = new ArrayList<>();
        newsContentList.add(new NewsContent("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                "Theo diễn biến...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentList.add(new NewsContent(null,
                "Tình tiết giảm nhẹ không đủ khoan hồng",
                "Luận vội, Viện kiểm sát đánh giá bị cáo...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentList.add(new NewsContent(null,
                "Gần 1 tháng TAND...",
                "Theo diễn biến...",
                R.drawable.rack,
                "Jack 5cu",
                null));
        newsContentAdapter = new NewsContentAdapter(this, newsContentList);
        rvContent.setAdapter(newsContentAdapter);
    }
    private void loadSpNews() {
        rvSpNews = findViewById(R.id.rvSpNews);
        rvSpNews.setHasFixedSize(true);
        rvSpNews.setLayoutManager(new GridLayoutManager(this, 1));
        spNewsList = new ArrayList<>();
        spNewsList.add(new NewsContent("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.rack));
        spNewsList.add(new NewsContent("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.rack));
        spNewsList.add(new NewsContent("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
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
    }
}