package com.example.newsandroidproject.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.MainActivity;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.CategoryAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.AuthenticationApi;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.common.NumParser;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.BodyItem;
import com.example.newsandroidproject.model.dto.AuthenticationResponse;
import com.example.newsandroidproject.model.dto.BookmarkRequest;
import com.example.newsandroidproject.model.dto.CommentLoadingResponse;
import com.example.newsandroidproject.model.dto.CommentPostingRequest;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.CommentItemModel;
import com.example.newsandroidproject.model.viewmodel.NewsContentModel;
import com.example.newsandroidproject.adapter.CommentDialogAdapter;
import com.example.newsandroidproject.adapter.NewsContentAdapter;
import com.example.newsandroidproject.adapter.SpecialNewsAdapter;
import com.example.newsandroidproject.model.viewmodel.UserCommentDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.JustifyContent;
public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton btnBack;
    ImageView imgAvar;
    TextView txtUserName, txtFollower, txtDate, txtNoSaved, txtNoViewed, txtNoCommented;
    Button btn_cate1, btn_cate2, btn_cate3, btn_more;
    RecyclerView rvContent, rvSpNews, rvCategories;
    NewsContentAdapter newsContentAdapter;
    SpecialNewsAdapter specialNewsAdapter;
    SeekBar sbFontSize;

    ImageButton btnFontFamily, btnComment, btnHistory, btnBookMark, btnBookMarkSaved;
    private Long articleId;

    private ArticleInReadingPageDTO article;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);


        toolbar = findViewById(R.id.topToolBarReadingPage);
        btnBack = findViewById(R.id.btnBack);
        csUserInfo = findViewById(R.id.csUserInfo);
        tvOrtheNews = findViewById(R.id.textView_OtherNews);
        imgAvar = findViewById(R.id.imgAvar);
        txtUserName = findViewById(R.id.txtUserName);
        txtFollower = findViewById(R.id.txtFollower);
        txtDate = findViewById(R.id.txtDate);
        txtNoSaved = findViewById(R.id.txtNoSaved);
        txtNoViewed = findViewById(R.id.txtNoViewed);
        txtNoCommented = findViewById(R.id.txtNoCommented);
        bottomToolBar = findViewById(R.id.bottomToolBarReadingPage);
        rvContent = findViewById(R.id.rvContent);
        rvCategories = findViewById(R.id.rvCategories);
        rvSpNews = findViewById(R.id.rvSpNews);
        sbFontSize = findViewById(R.id.sbFontSize);
        btnFontFamily = findViewById(R.id.btnFontFamily);
        btnComment = findViewById(R.id.btnCommentScroll);
        btnHistory = findViewById(R.id.btnHistory);
        btnBookMark = findViewById(R.id.btnBookmark);
        btnBookMarkSaved = findViewById(R.id.btnBookmarkSaved);
        pb = findViewById(R.id.progressBarLoadingArticleContent);





        EdgeToEdge.enable(this);
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

        loadindEffect(true);
        call.enqueue(new Callback<ArticleInReadingPageDTO>() {
            @Override
            public void onResponse(Call<ArticleInReadingPageDTO> call, Response<ArticleInReadingPageDTO> response) {
                if (response.code() == 200 && response.body() != null) {
                    article = response.body();
//                    System.out.println("oncreate: "+ article.getBodyItemList().get(2).getContent().toString());

                    Toast.makeText(ReadingActivity.this, article.isSaved() + " " + article.isSeeLater(), Toast.LENGTH_SHORT).show();

                    loadindEffect(false);

                    loadTopToolBar();
                    loadAuthor();
                    loadCategories();
                    loadContent();
                    loadSpNews();
                    loadBottomToolBar();

                    ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                    Call<BookmarkRequest> call2 = apiService.viewArticle(new BookmarkRequest(null, articleId, DateParser.formatToISO8601(new Date())));
                    call2.enqueue(new Callback<BookmarkRequest>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BookmarkRequest> call, Response<BookmarkRequest> response) {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(ReadingActivity.this, "Đã lưu vào lịch sử xem!", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<BookmarkRequest> call, Throwable t) {
                            Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });
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
    ConstraintLayout csUserInfo;
    TextView tvOrtheNews;

    Toolbar bottomToolBar;
    private void loadAuthor() {
        imgAvar.setImageResource(R.drawable.ic_blank_avatar);
        if (article.getAvatar() != null) {
            byte[] avatarByteData = Base64.decode(article.getAvatar(), Base64.DEFAULT);
            imgAvar.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
        } else {
            imgAvar.setImageResource(R.drawable.ic_blank_avatar);
        }

        txtUserName.setText(article.getUserName());
        txtFollower.setText(NumParser.numParse(article.getFollowCount()) + " người theo dõi");
        txtDate.setText(DateParser.dateFormat(article.getCreateTime()));
        txtNoSaved.setText(NumParser.numParse(article.getSaveCount()));
        txtNoViewed.setText(NumParser.numParse(article.getViewCount()));
        txtNoCommented.setText(NumParser.numParse(article.getCommentCount()));
    }

    private void loadCategories() {
        // Set FlexboxLayoutManager with the desired configurations
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        rvCategories.setLayoutManager(flexboxLayoutManager);

        // Set the adapter
        CategoryAdapter adapter = new CategoryAdapter(this, article.getCategories());
        rvCategories.setAdapter(adapter);
    }


    private void loadContent() {
        rvContent.setHasFixedSize(true);
        rvContent.setLayoutManager(new GridLayoutManager(this, 1));
//        System.out.println(article.getBodyItemList().get(2).getContent().toString());

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
    private List<ArticleInNewsFeedModel> spNewsList;
    private void loadSpNews() {
        rvSpNews.setHasFixedSize(true);
        rvSpNews.setLayoutManager(new GridLayoutManager(this, 1));
        spNewsList = article.getProposal();
        specialNewsAdapter = new SpecialNewsAdapter(this, spNewsList);
        rvSpNews.setAdapter(specialNewsAdapter);
    }
    private void loadBottomToolBar() {
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
                initAndShowDialog();
            }
        });


        if (article.isSaved() == 1) {
            btnBookMark.setTag("saved");
            btnBookMark.setImageResource(R.drawable.bookmark_fill_svgrepo_com);
            btnBookMark.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.saved), PorterDuff.Mode.SRC_IN);

        } else {
            btnBookMark.setTag("unsaved");
            btnBookMark.setImageResource(R.drawable.ic_bookmarked_unsaved);
            btnBookMark.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);
        }
        if (article.isSeeLater() == 1) {
            btnHistory.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.saved2), PorterDuff.Mode.SRC_IN);
            btnHistory.setTag("saved");
        } else {
            btnHistory.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);
            btnHistory.setTag("unsaved");
        }        btnBookMark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if ("unsaved".equals(btnBookMark.getTag())) {
                    btnBookMark.setImageResource(R.drawable.bookmark_fill_svgrepo_com);
                    btnBookMark.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.saved), PorterDuff.Mode.SRC_IN);
                    btnBookMark.setTag("saved");

                    ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                    Call<BookmarkRequest> call = apiService.saveBookMark(new BookmarkRequest(null, articleId, DateParser.formatToISO8601(new Date())));
                    call.enqueue(new Callback<BookmarkRequest>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BookmarkRequest> call, Response<BookmarkRequest> response) {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(ReadingActivity.this, "Đã lưu vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<BookmarkRequest> call, Throwable t) {
                            Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    btnBookMark.setImageResource(R.drawable.ic_bookmarked_unsaved);
                    btnBookMark.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);
                    btnBookMark.setTag("unsaved");

                    ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                    Call<BookmarkRequest> call = apiService.abortBookMark(new BookmarkRequest(null, articleId, DateParser.formatToISO8601(new Date())));
                    call.enqueue(new Callback<BookmarkRequest>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BookmarkRequest> call, Response<BookmarkRequest> response) {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(ReadingActivity.this, "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<BookmarkRequest> call, Throwable t) {
                            Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if ("unsaved".equals(btnHistory.getTag())) {
                    btnHistory.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.saved2), PorterDuff.Mode.SRC_IN);
                    btnHistory.setTag("saved");

                    ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                    Call<BookmarkRequest> call = apiService.saveSeeLater(new BookmarkRequest(null, articleId, DateParser.formatToISO8601(new Date())));
                    call.enqueue(new Callback<BookmarkRequest>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BookmarkRequest> call, Response<BookmarkRequest> response) {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(ReadingActivity.this, "Đã lưu vào danh sách xem sau!", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<BookmarkRequest> call, Throwable t) {
                            Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    btnHistory.setColorFilter(ContextCompat.getColor(ReadingActivity.this, R.color.primaryTextColor), PorterDuff.Mode.SRC_IN);
                    btnHistory.setTag("unsaved");

                    ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                    Call<BookmarkRequest> call = apiService.abortSeeLater(new BookmarkRequest(null, articleId, DateParser.formatToISO8601(new Date())));
                    call.enqueue(new Callback<BookmarkRequest>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<BookmarkRequest> call, Response<BookmarkRequest> response) {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(ReadingActivity.this, "Đã xóa khỏi danh sách xem sau!", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<BookmarkRequest> call, Throwable t) {
                            Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
    private Integer commentPageIndex;
    private Long commentCount;
    private Integer maxPageIndex;
    @SuppressLint("NotifyDataSetChanged")
    private void initAndShowDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comment);
        commentPageIndex = 1;
        TextView txtNoCommentedOfNews = dialog.findViewById(R.id.txtNoCommentedOfNews);
        RecyclerView rvCommentList = dialog.findViewById(R.id.rvCommentList);
//        Button btnLoadMoreComment = dialog.findViewById(R.id.btnLoadmoreComment);
        CommentDialogAdapter commentDialogAdapter;
        EditText edtCommentDialog = dialog.findViewById(R.id.edtCommentDialog);
        ImageButton btnSent = dialog.findViewById(R.id.btnSent);
        List<UserCommentDTO> commentItemModelList = new UniqueList<>();
        txtNoCommentedOfNews.setText("");
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        LinearLayout llComment = dialog.findViewById(R.id.llComment);
        rvCommentList.setHasFixedSize(true);
        rvCommentList.setLayoutManager(new GridLayoutManager(this, 1));
        commentDialogAdapter = new CommentDialogAdapter(this, commentItemModelList, true);

        commentDialogAdapter.setLoadMoreAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming the load more button is clicked
                // Find the view holder that contains the progress bar
                RecyclerView.ViewHolder viewHolder = rvCommentList.findViewHolderForAdapterPosition(commentItemModelList.size());
                if (viewHolder instanceof CommentDialogAdapter.LoadMoreHolder) {
                    ProgressBar loadMoreProgressBar = ((CommentDialogAdapter.LoadMoreHolder) viewHolder).progressBar;
                    loadMoreComments(commentDialogAdapter, commentItemModelList, txtNoCommentedOfNews, loadMoreProgressBar);
                }
            }
        });

        commentDialogAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            boolean isInit = true;
            @Override
            public void onChanged() {
                super.onChanged();
                if (isInit)
                {
                    handleLoadMoreProgressBar();
                    isInit = false;
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }

            private void handleLoadMoreProgressBar() {
                rvCommentList.post(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.ViewHolder viewHolder = rvCommentList.findViewHolderForAdapterPosition(commentItemModelList.size());
                        if (viewHolder instanceof CommentDialogAdapter.LoadMoreHolder) {
                            ProgressBar loadMoreProgressBar = ((CommentDialogAdapter.LoadMoreHolder) viewHolder).progressBar;
                            loadInitialComments(commentDialogAdapter, commentItemModelList, txtNoCommentedOfNews, loadMoreProgressBar);
                        }
                    }
                });
            }
        });

        rvCommentList.setAdapter(commentDialogAdapter);
        commentDialogAdapter.notifyDataSetChanged();

        dialog.show();
        // Lấy chiều cao của màn hình
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // Tính chiều cao tối thiểu của dialog (2/3 chiều cao màn hình)
        int minHeight = (int) (screenHeight * 4 / 5);

        // Đặt chiều cao của dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, minHeight);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                commentPageIndex = 1;
            }
        });
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtCommentDialog.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(ReadingActivity.this, "Nội dung bình luận không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Hiển thị ProgressBar và ẩn nút gửi
                btnSent.setVisibility(View.GONE);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);

                ArticleApi apiService = RetrofitService.getClient(ReadingActivity.this).create(ArticleApi.class);
                Call<UserCommentDTO> call = apiService.postComment(new CommentPostingRequest(content, DateParser.formatToISO8601(new Date()), DateParser.formatToISO8601(new Date()), articleId, null, null));
                call.enqueue(new Callback<UserCommentDTO>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<UserCommentDTO> call, Response<UserCommentDTO> response) {
                        // Ẩn ProgressBar và hiển thị lại nút gửi
                        progressBar.setVisibility(View.GONE);
                        btnSent.setVisibility(View.VISIBLE);

                        if (response.code() == 200 && response.body() != null) {
                            // Thêm bình luận vào đầu danh sách
                            commentItemModelList.add(0, response.body());
                            commentDialogAdapter.notifyItemInserted(0);
                            edtCommentDialog.setText(""); // Xóa nội dung trong EditText

                            // Cuộn lên phần tử đầu tiên
                            rvCommentList.scrollToPosition(0);

                            // Lấy View của bình luận mới và áp dụng hiệu ứng dần hiện ra
                            rvCommentList.post(new Runnable() {
                                @Override
                                public void run() {
                                    RecyclerView.ViewHolder viewHolder = rvCommentList.findViewHolderForAdapterPosition(0);
                                    if (viewHolder != null) {
                                        // Hiệu ứng fade-in cho bình luận mới
                                        viewHolder.itemView.setAlpha(0);
                                        viewHolder.itemView.animate().alpha(1).setDuration(4000).start();

                                        final TextView txtCommentTime = ((CommentDialogAdapter.CommentDialogHolder) viewHolder).txtCommentTime;
                                        int startColor = ContextCompat.getColor(ReadingActivity.this, R.color.newcomment_200);  // replace R.color.blue with your actual blue color resource
                                        int endColor = ContextCompat.getColor(ReadingActivity.this, R.color.primaryTextColor);    // replace R.color.gray with your actual gray color resource

                                        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
                                        colorAnimator.setDuration(10000);  // 2 seconds
                                        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animator) {
                                                txtCommentTime.setTextColor((int) animator.getAnimatedValue());
                                            }
                                        });
                                        colorAnimator.start();
                                    }
                                }
                            });
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
                    public void onFailure(Call<UserCommentDTO> call, Throwable t) {
                        // Ẩn ProgressBar và hiển thị lại nút gửi
                        progressBar.setVisibility(View.GONE);
                        btnSent.setVisibility(View.VISIBLE);

                        Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
        private void loadindEffect(boolean isLoading) {
            if (isLoading) {
                pb.setVisibility(View.VISIBLE);
                tvOrtheNews.setVisibility(View.GONE);
                csUserInfo.setVisibility(View.GONE);
                rvCategories.setVisibility(View.GONE);
                rvContent.setVisibility(View.GONE);
                rvSpNews.setVisibility(View.GONE);
                bottomToolBar.setVisibility(View.GONE);
            } else {
                pb.setVisibility(View.GONE);
                tvOrtheNews.setVisibility(View.VISIBLE);
                csUserInfo.setVisibility(View.VISIBLE);
                rvCategories.setVisibility(View.VISIBLE);
                rvContent.setVisibility(View.VISIBLE);
                rvSpNews.setVisibility(View.VISIBLE);
                bottomToolBar.setVisibility(View.VISIBLE);
            }
        }
// Hàm tạo hiệu ứng dần hiện ra
        private void animateVisibility(View view) {
            view.setVisibility(View.INVISIBLE); // Bắt đầu từ trạng thái ẩn
            AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f); // Hiệu ứng từ trong suốt tới không trong suốt
            animation.setDuration(1000); // 2 giây
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setVisibility(View.VISIBLE); // Đặt trạng thái thành hiển thị khi bắt đầu animation
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Không cần thực hiện hành động gì thêm
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Không cần thực hiện hành động gì thêm
                }
            });
            view.startAnimation(animation);
        }
    private void loadInitialComments(CommentDialogAdapter adapter, List<UserCommentDTO> commentList, TextView txtNoCommentedOfNews, ProgressBar processBar) {
        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        processBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar
        Call<CommentLoadingResponse> call = apiService.getCommentsByArticleId(articleId, commentPageIndex++);
        call.enqueue(new Callback<CommentLoadingResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<CommentLoadingResponse> call, Response<CommentLoadingResponse> response) {
                processBar.setVisibility(View.GONE); // Hiển thị ProgressBar
                if (response.code() == 200 && response.body() != null) {
                    CommentLoadingResponse result = response.body();
                    commentList.addAll(result.getComments());
                    commentCount = result.getCommentCount();
                    maxPageIndex = result.getMaxPageIndex();
                    txtNoCommentedOfNews.setText(commentCount + " bình luận");
                    adapter.notifyDataSetChanged();
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
            public void onFailure(Call<CommentLoadingResponse> call, Throwable t) {
                Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreComments(CommentDialogAdapter adapter, List<UserCommentDTO> commentList, TextView txtNoCommentedOfNews, ProgressBar loadMoreProgressBar) {
        loadMoreProgressBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar

        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        Call<CommentLoadingResponse> call = apiService.getCommentsByArticleId(articleId, commentPageIndex < maxPageIndex ? commentPageIndex++ : maxPageIndex);
        call.enqueue(new Callback<CommentLoadingResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<CommentLoadingResponse> call, Response<CommentLoadingResponse> response) {
                loadMoreProgressBar.setVisibility(View.GONE); // Ẩn ProgressBar

                if (response.code() == 200 && response.body() != null) {
                    CommentLoadingResponse result = response.body();
                    int before = commentList.size();
                    commentList.addAll(result.getComments());
                    int after = commentList.size();
                    commentCount = result.getCommentCount();
                    maxPageIndex = result.getMaxPageIndex();
                    txtNoCommentedOfNews.setText(commentCount + " bình luận");
                    adapter.notifyItemRangeInserted(before, after - before);
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
            public void onFailure(Call<CommentLoadingResponse> call, Throwable t) {
                loadMoreProgressBar.setVisibility(View.GONE); // Ẩn ProgressBar

                Toast.makeText(ReadingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ReadingActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}