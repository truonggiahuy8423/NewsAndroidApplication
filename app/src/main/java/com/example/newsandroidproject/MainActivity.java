package com.example.newsandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsandroidproject.activity.UserInfoActivity;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.fragment.HistoryFragment;
import com.example.newsandroidproject.fragment.HomeFragment;
import com.example.newsandroidproject.fragment.NotificationFragment;
import com.example.newsandroidproject.fragment.ScrollModeFragment;
import com.example.newsandroidproject.fragment.SettingFragment;
import com.example.newsandroidproject.fragment.FavoriteFragment;
import com.example.newsandroidproject.databinding.ActivityMainBinding;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.UserNavigationMenu;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//import android.databinding.DataBindingUtil;



public class MainActivity extends AppCompatActivity {
    private UserApi userApi;
    private Fragment homeFragment;
    private Fragment scrollModeFragment;
    private Fragment notificationFragment;
    private Fragment settingFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_drawer;
    private UserNavigationMenu userNavigationMenu;
    private View headerView;
    private ShapeableImageView ivAvarMenu;
    private TextView txtUsernameMenu, txtEmailMenu;

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
        navigation_drawer = findViewById(R.id.navigation_drawer);
        headerView = navigation_drawer.getHeaderView(0);

        // Tìm ShapeableImageView, TextView trong header view
        ivAvarMenu = headerView.findViewById(R.id.ivAvarMenu);
        txtUsernameMenu = headerView.findViewById(R.id.txtUsernameMenu);
        txtEmailMenu = headerView.findViewById(R.id.txtEmailMenu);

        changeFragment(homeFragment);

        test();

        setUpNavigationMenu();

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

    public void setOpenNavigationBar() {
        drawerLayout.openDrawer(GravityCompat.START);
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

    private void setUpNavigationMenu() {
        userApi = RetrofitService.getClient(this).create(UserApi.class);
        userApi.getUserNavigationMenu().enqueue(new Callback<UserNavigationMenu>() {
            @Override
            public void onResponse(Call<UserNavigationMenu> call, Response<UserNavigationMenu> response) {
                if(response.body() != null){
                    userNavigationMenu = response.body();
                    setUpEventNavigationMenu(userNavigationMenu.getUserId());
                    if (userNavigationMenu.getAvatar() != null) {
                        byte[] avatarByteData = Base64.decode(userNavigationMenu.getAvatar(), Base64.DEFAULT);
                        ivAvarMenu.setImageBitmap(BitmapFactory.decodeByteArray(avatarByteData, 0, avatarByteData.length));
                    }
                    else{
                        ivAvarMenu.setImageResource(R.drawable.ic_blank_avatar);
                    }
                    txtUsernameMenu.setText(userNavigationMenu.getName());
                    txtEmailMenu.setText(userNavigationMenu.getEmail());
                }
                else{
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
            public void onFailure(Call<UserNavigationMenu> call, Throwable throwable) {
                Log.d("Test API", "Failure: " + throwable.getMessage());
            }
        });

    }
    private void setUpEventNavigationMenu(Long userId){
        ivAvarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserInfoPage(userId);
            }
        });
        txtUsernameMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserInfoPage(userId);
            }
        });
        txtEmailMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserInfoPage(userId);
            }
        });
        navigation_drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.ic_user_menu ){
                    goToUserInfoPage(userId);
                }else if(menuItem.getItemId() == R.id.ic_history_navigation){
                    openHistoryFragment();
                }
                else if(menuItem.getItemId() == R.id.ic_help_navigation){
                    Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                }
                else if(menuItem.getItemId() == R.id.ic_about_navigation){
                    Toast.makeText(MainActivity.this, "About us", Toast.LENGTH_SHORT).show();
                }
                else if(menuItem.getItemId() == R.id.ic_logout_navigation){
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi xử lý
                return true;
            }
        });
    }
    private void goToUserInfoPage(Long userId) {
        Intent myIntent = new Intent(MainActivity.this, UserInfoActivity.class);
        Bundle myBunble = new Bundle();
        myBunble.putLong("userId", userId);

        myIntent.putExtra("myPackage", myBunble);
        startActivity(myIntent);
    }
}