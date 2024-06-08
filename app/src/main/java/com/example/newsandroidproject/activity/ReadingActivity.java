package com.example.newsandroidproject.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.AuthenticationApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.CommentItemModel;
import com.example.newsandroidproject.model.viewmodel.NewsContentModel;
import com.example.newsandroidproject.adapter.CommentDialogAdapter;
import com.example.newsandroidproject.adapter.NewsContentAdapter;
import com.example.newsandroidproject.adapter.SpecialNewsAdapter;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton btnBack;
    ImageView imgAvar;
    TextView txtUserName, txtFollower, txtDate, txtNoSaved, txtNoViewed, txtNoCommented;
    Button btn_cate1, btn_cate2, btn_cate3, btn_more;
    RecyclerView rvContent, rvSpNews;
    NewsContentAdapter newsContentAdapter;
    SpecialNewsAdapter specialNewsAdapter;
    SeekBar sbFontSize;
    ImageButton btnFontFamily, btnComment, btnHistory, btnBookMark, btnBookMarkSaved;
    private Long articleId;
    private ArticleInReadingPageDTO article;

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

        Intent intent = getIntent();
        articleId = (long)intent.getLongExtra("articleId", (long)-1);
        if (articleId == (long)-1) {
            Toast.makeText(this, "Bài viết không tìm thấy!", Toast.LENGTH_SHORT).show();
        }

        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        Call<ArticleInReadingPageDTO> call = apiService.getArticleById(articleId);

        call.enqueue(new Callback<ArticleInReadingPageDTO>() {
            @Override
            public void onResponse(Call<ArticleInReadingPageDTO> call, Response<ArticleInReadingPageDTO> response) {
                if (response.code() == 200 && response.body() != null) {
                    article = response.body();
                    System.out.println("oncreate: "+ article.getBodyItemList().get(2).getContent().toString());

                    loadTopToolBar();
                    loadAuthor();
                    loadCategories();
                    loadContent();
                    loadSpNews();
                    loadBottomToolBar();
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(ReadingActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ReadingActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleInReadingPageDTO> call, Throwable t) {
                Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("LoginActivity", "Error: " + t.getMessage());
                Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });


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
        txtUserName.setText(article.getUserName());
        txtFollower.setText(shortenNumber(article.getFollowCount()) + " người theo dõi");
        txtDate.setText(article.getCreateTime().toString());
        txtNoSaved.setText(shortenNumber(-1));
        txtNoViewed.setText(shortenNumber(article.getViewCount()));
        txtNoCommented.setText(shortenNumber(article.getCommentCount()));
    }
    private String shortenNumber(long n){
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
        System.out.println(article.getBodyItemList().get(2).getContent().toString());

        BodyItem header = new BodyItem();
        header.setArticleTitle(article.getTitle());
        header.setDataImage(article.getThumbnail());
        header.setImageName(article.getThumbnailName());

        BodyItem description = new BodyItem();
        description.setBodyTitle(article.getDescription());

        article.getBodyItemList().add(0, header);
        article.getBodyItemList().add(1, description);

        newsContentAdapter = new NewsContentAdapter(this, article.getBodyItemList());
        rvContent.setAdapter(newsContentAdapter);
    }
    private List<NewsContentModel> spNewsList;
    private void loadSpNews() {
        rvSpNews = findViewById(R.id.rvSpNews);
        rvSpNews.setHasFixedSize(true);
        rvSpNews.setLayoutManager(new GridLayoutManager(this, 1));
        spNewsList = new ArrayList<>();
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án Trương Mỹ Lan",
                "Gần 1 tháng TAND...",
                R.drawable.thumbnail));
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.thumbnail));
        spNewsList.add(new NewsContentModel("Nhiều tranh cãi chờ tòa phán quyết trong vụ án TML",
                "Gần 1 tháng TAND...",
                R.drawable.thumbnail));
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
            @SuppressLint("NotifyDataSetChanged")
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