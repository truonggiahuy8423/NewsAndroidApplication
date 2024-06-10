package com.example.newsandroidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.fragment.HistoryFragment;
import com.example.newsandroidproject.fragment.HomeFragment;
import com.example.newsandroidproject.fragment.NotificationFragment;
import com.example.newsandroidproject.fragment.ScrollModeFragment;
import com.example.newsandroidproject.fragment.SettingFragment;
import com.example.newsandroidproject.fragment.FavoriteFragment;
import com.example.newsandroidproject.databinding.ActivityMainBinding;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInNewsFeedModel;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
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
    public void setOpenNavigationBar() {
        Toast.makeText(this, "Button2 clicked!", Toast.LENGTH_SHORT).show();
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void test() {
        ArticleApi apiService = RetrofitService.getClient(this).create(ArticleApi.class);
        apiService.getArticleById((long)1).enqueue(new Callback<ArticleInReadingPageDTO>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ArticleInReadingPageDTO> call, Response<ArticleInReadingPageDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        System.out.println(response.body());
                    }
                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
                        Toast.makeText(MainActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("Test API", errorResponse.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
//                        Log.d("Test API", "Failure 2");

                    }
                }
            }
            @Override
            public void onFailure(Call<ArticleInReadingPageDTO> call, Throwable t) {
                Log.d("Test API", "Failure: " + t.getMessage());
            }
        });
    }
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment = new HomeFragment();
        scrollModeFragment = new ScrollModeFragment();
        notificationFragment = new NotificationFragment();
        settingFragment = new SettingFragment();
        drawerLayout = findViewById(R.id.drawer_layout);
        changeFragment(homeFragment);

        test();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> { //ok
                if (item.getItemId() == R.id.home_page)
                    changeFragment(homeFragment);
                else if (item.getItemId() == R.id.scroll_mode_page)
                    changeFragment(scrollModeFragment);
                else if (item.getItemId() == R.id.notification_page)
                    changeFragment(notificationFragment);
                else if (item.getItemId() == R.id.setting_page)
                    changeFragment(settingFragment);
            return true;
        });

    }
    private void changeFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, f);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void openHistoryFragment() {
        Fragment historyFragment = new HistoryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, historyFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openFavoriteFragment() {
        Fragment favoriteFragment = new FavoriteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, favoriteFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openSettingFragment() {
        Fragment settingFragment = new SettingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, settingFragment)
                .addToBackStack(null)
                .commit();
    }
}