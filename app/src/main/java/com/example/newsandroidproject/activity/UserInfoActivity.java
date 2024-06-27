package com.example.newsandroidproject.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsandroidproject.R;
import com.example.newsandroidproject.adapter.ArticleUserInfoAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.FollowApi;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.DateParser;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.model.Follow;
import com.example.newsandroidproject.model.User;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleUserInfoDTO;
import com.example.newsandroidproject.model.viewmodel.UserInfoDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    private UserApi userApi;
    private ArticleApi articleApi;
    private FollowApi followApi;
    private UserInfoDTO userInfoDTO;
    private TextView txtRoleUserInfo, txtUsername,
            txtNoPostUserInfo, txtNoFollowing, txtNoFollowed,
            txtStateUserInfo;
    private ShapeableImageView ivAvarUserInfo;
    private Button btnFollow;
    private ImageView btnBackUserInfo;
    private ConstraintLayout csActivityUserInfo;
    private boolean isToggled;
    private RecyclerView rvArticleUserInfo;
    private ArticleUserInfoAdapter articleUserInfoAdapter;
    private UniqueList<ArticleUserInfoDTO> articleList;
    private int page_index = 1;
    private Follow follow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.csActivityUserInfo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent myintent = getIntent();
        Bundle myBundle = myintent.getBundleExtra("myPackage");
        Long userId = myBundle.getLong("userId");
        initComponent();
        callUserApi(userId);
    }

    private void initComponent() {
        txtRoleUserInfo = findViewById(R.id.txtRoleUserInfo);
        txtUsername = findViewById(R.id.txtUsername);
        txtNoPostUserInfo = findViewById(R.id.txtNoPostUserInfo);
        txtNoFollowed = findViewById(R.id.txtNoFollowed);
        txtNoFollowing = findViewById(R.id.txtNoFollowing);
        ivAvarUserInfo = findViewById(R.id.ivAvarUserInfo);
        btnFollow = findViewById(R.id.btnFollowUserInfo);
        btnBackUserInfo = findViewById(R.id.btnBackUserInfo);
        csActivityUserInfo = findViewById(R.id.csActivityUserInfo);
        rvArticleUserInfo = findViewById(R.id.rvArticleUserInfo);
        txtStateUserInfo = findViewById(R.id.txtStateUserInfo);
    }

    private void callUserApi(Long userId) {
        userApi = RetrofitService.getClient(this).create(UserApi.class);
        userApi.getUserInfo(userId).enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.body() != null){
                    userInfoDTO = response.body();
                    setDataForComponents(userId);
                }
                else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(UserInfoActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(UserInfoActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable throwable) {
                Log.d("Test API", "Failure: " + throwable.getMessage());
            }
        });
    }

    private void setDataForComponents(Long userId) {
        String roleName = "";
        Long loginUserId = userInfoDTO.getLoginUser();
        if(userInfoDTO.getRole() == 1){
            roleName = "Author";
            setStateOfFollowButton(userInfoDTO.getIsFollowedByLoginUser());
            setUpAdapter(userId);
            callAricleApi(userId);
        }
        else{
            roleName = "User";
            rvArticleUserInfo.setVisibility(View.GONE);
            txtStateUserInfo.setVisibility(View.VISIBLE);
        }
        txtRoleUserInfo.setText(roleName);
        txtUsername.setText(userInfoDTO.getName());
        txtNoPostUserInfo.setText(String.valueOf(userInfoDTO.getPostCount()));
        txtNoFollowed.setText(String.valueOf(userInfoDTO.getFollowedCount()));
        txtNoFollowing.setText(String.valueOf(userInfoDTO.getFollowingCount()));

        if (userInfoDTO.getAvatar() != null) {
            byte[] avatarByteData = Base64.decode(userInfoDTO.getAvatar(), Base64.DEFAULT);
            ivAvarUserInfo.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
        }else{
            ivAvarUserInfo.setImageResource(R.drawable.ic_blank_avatar);
        }

        setEventForComponents(userId, userInfoDTO.getLoginUser(), userInfoDTO.getRole());
    }

    private void setUpAdapter(Long userId) {
        articleList = new UniqueList<>();
        articleUserInfoAdapter = new ArticleUserInfoAdapter(UserInfoActivity.this, articleList);
        rvArticleUserInfo.setAdapter(articleUserInfoAdapter);
        rvArticleUserInfo.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this));
        rvArticleUserInfo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rvArticleUserInfo.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == articleUserInfoAdapter.getItemCount() - 1) {
                    callAricleApi(userId);
                    Log.d("Test", "Đã đến item cuối cùng");
                }
            }
        });
    }

    private void callAricleApi(Long userId) {
        articleApi = RetrofitService.getClient(this).create(ArticleApi.class);
        articleApi.getArticlesUserInfo(userId, page_index++).enqueue(new Callback<List<ArticleUserInfoDTO>>() {
            @Override
            public void onResponse(Call<List<ArticleUserInfoDTO>> call, Response<List<ArticleUserInfoDTO>> response) {
                if(response.body() != null){
                    articleList.addAll(response.body());
                    articleUserInfoAdapter.notifyDataSetChanged();
                }
                else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(UserInfoActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(UserInfoActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ArticleUserInfoDTO>> call, Throwable throwable) {
                Log.d("Test API", "Failure: " + throwable.getMessage());
            }
        });
    }

    private void setStateOfFollowButton(boolean isToggled){
        System.out.println("Đang set follow button");
        if (!isToggled) {
//            System.out.println("Chưa follow");
            btnFollow.setBackgroundResource(R.drawable.custom_btn_unfollow);

            btnFollow.setText("Follow");
            btnFollow.setTextColor(ContextCompat.getColor(UserInfoActivity.this, R.color.white));

            btnFollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        } else {
//            System.out.println("Đang follow");
            btnFollow.setBackgroundResource(R.drawable.custom_btn_following);

            btnFollow.setText("Following");
            btnFollow.setTextColor(ContextCompat.getColor(UserInfoActivity.this, R.color.myprimary));

            Drawable ic_checked_drawable = ContextCompat.getDrawable(UserInfoActivity.this, R.drawable.ic_checked);
            ic_checked_drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(UserInfoActivity.this, R.color.myprimary), PorterDuff.Mode.SRC_IN));
            btnFollow.setCompoundDrawablesWithIntrinsicBounds(null, null, ic_checked_drawable, null);
        }
    }

    private void setEventForComponents(Long userId, Long loginUserId, Integer userRole) {
        if(userRole == 1 && userId != loginUserId){
                btnFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UserInfoActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                        TransitionManager.beginDelayedTransition(csActivityUserInfo);
                        isToggled = !isToggled;
                        System.out.println("Click event: " + isToggled);
                        if(isToggled){
                            System.out.println("Click to set Follow");
                            follow(userId, loginUserId);
                        }else{
                            System.out.println("Click to set Unfollow");
                            unfollow(userId, loginUserId);
                        }
                        setStateOfFollowButton(isToggled);
                    }
                });

        }else{
            btnFollow.setVisibility(View.GONE);
        }

        btnBackUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void follow(Long userId, Long loginUserId) {
        follow = new Follow(userId, loginUserId,DateParser.formatToISO8601(new Date()));

        followApi = RetrofitService.getClient(this).create(FollowApi.class);
        Call<Follow> call = followApi.createFollow(follow);
        call.enqueue(new Callback<Follow>() {
            @Override
            public void onResponse(Call<Follow> call, Response<Follow> response) {
                if (response.isSuccessful()) {
                    Log.d("UserInfoActivity", "Follow created: ");
                } else {
                    Log.d("UserInfoActivity", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Follow> call, Throwable t) {
                Log.d("UserInfoActivity", "Request failed: " + t.getMessage());
            }
        });
    }

    private void unfollow(Long followedId, Long loginUserId) {
        followApi = RetrofitService.getClient(this).create(FollowApi.class);
        Call<Void> call = followApi.deleteFollow(followedId, loginUserId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("UserInfoActivity", "Follow deleted");
                } else {
                    Log.d("UserInfoActivity", "Request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("UserInfoActivity", "Request failed: " + t.getMessage());
            }
        });
    }
}