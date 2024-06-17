package com.example.newsandroidproject;

import static com.example.newsandroidproject.fragment.HomeFragment.GET_ARTICLE_REQUEST_CODE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newsandroidproject.activity.LoginActivity;
import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.fragment.HistoryFragment;
import com.example.newsandroidproject.fragment.HomeFragment;
import com.example.newsandroidproject.fragment.NotificationFragment;
import com.example.newsandroidproject.fragment.ScrollModeFragment;
import com.example.newsandroidproject.fragment.SettingFragment;
import com.example.newsandroidproject.fragment.FavoriteFragment;
import com.example.newsandroidproject.databinding.ActivityMainBinding;
import com.example.newsandroidproject.model.dto.ArticleDTO;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.PostArticleRequestDTO;
import com.example.newsandroidproject.retrofit.RetrofitService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//import android.databinding.DataBindingUtil;


public class MainActivity extends AppCompatActivity {
    private Fragment homeFragment;
    private Fragment scrollModeFragment;
    private Fragment notificationFragment;
    private Fragment settingFragment;

    DrawerLayout drawerLayout;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate MainActivity");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeFragment = new HomeFragment();
        scrollModeFragment = new ScrollModeFragment();
        notificationFragment = new NotificationFragment();
        settingFragment = new SettingFragment();
        drawerLayout = findViewById(R.id.drawer_layout);

        // Add fragments initially to avoid recreation
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout, homeFragment, "homeFragment")
                    .add(R.id.frameLayout, scrollModeFragment, "scrollModeFragment").hide(scrollModeFragment)
                    .add(R.id.frameLayout, notificationFragment, "notificationFragment").hide(notificationFragment)
                    .add(R.id.frameLayout, settingFragment, "settingFragment").hide(settingFragment)
                    .commit();
        }

        // Initially display the home fragment
        showFragment(homeFragment);

        test();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_page) {
                showFragment(homeFragment);
            }
            if (item.getItemId() == R.id.scroll_mode_page) {
                showFragment(scrollModeFragment);
            }
            if (item.getItemId() == R.id.notification_page) {
                showFragment(notificationFragment);
            }
            if (item.getItemId() == R.id.setting_page) {
                showFragment(settingFragment);
            }
            return true;
        });
        postArticleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    System.out.println("COME BACK" + (result.getResultCode() == RESULT_OK));
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        System.out.println(data);
                        if (data != null && data.hasExtra("article")) {
                            System.out.println("COME BACK 2");
                            if (!data.getBooleanExtra("isDraft", true)) {
                                System.out.println("COME BACK 3");
                                PostArticleRequestDTO resultArticle = (PostArticleRequestDTO) data.getSerializableExtra("article");
                                ((HomeFragment) homeFragment).postArticle(resultArticle);
                            }
                        }
                    }
                });
//        startSecondActivityForResult();
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment frag : getSupportFragmentManager().getFragments()) {
            if (frag != null && frag.isVisible()) {
                transaction.hide(frag);
            }
        }
        transaction.show(fragment);
        transaction.commit();
    }

    public void setOpenNavigationBar() {
        Toast.makeText(this, "Button2 clicked!", Toast.LENGTH_SHORT).show();
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void test() {
        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        apiService.getArticleById((long) 1).enqueue(new Callback<ArticleInReadingPageDTO>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ArticleInReadingPageDTO> call, Response<ArticleInReadingPageDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
//                        System.out.println(response.body());
                    }
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(MainActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleInReadingPageDTO> call, Throwable t) {
                Log.d("Test API", "Failure: " + t.getMessage());
            }
        });
    }

    public void openHistoryFragment() {
        Fragment historyFragment = new HistoryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, historyFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openFavoriteFragment() {
        Fragment favoriteFragment = new FavoriteFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, favoriteFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openSettingFragment() {
        Fragment settingFragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, settingFragment)
                .addToBackStack(null)
                .commit();
    }

    // Method called by fragment to start SecondActivity
    private ActivityResultLauncher<Intent> postArticleLauncher;

    public void startSecondActivityForResult() {
        Intent intent = new Intent(this, PostArticleActivity.class);
        postArticleLauncher.launch(intent);
    }


}