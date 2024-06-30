package com.example.newsandroidproject;

import static com.example.newsandroidproject.fragment.HomeFragment.GET_ARTICLE_REQUEST_CODE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.content.Intent;
import androidx.annotation.Nullable;
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

import com.example.newsandroidproject.activity.LoginActivity;
import com.example.newsandroidproject.activity.PostArticleActivity;
import com.example.newsandroidproject.activity.UserInfoActivity;
import com.example.newsandroidproject.adapter.NotificationAdapter;
import com.example.newsandroidproject.api.ArticleApi;
import com.example.newsandroidproject.api.NotificationApi;
import com.example.newsandroidproject.api.UserApi;
import com.example.newsandroidproject.common.JsonParser;
import com.example.newsandroidproject.common.UniqueList;
import com.example.newsandroidproject.fragment.HistoryFragment;
import com.example.newsandroidproject.fragment.HomeFragment;
import com.example.newsandroidproject.fragment.NotificationFragment;
import com.example.newsandroidproject.fragment.ScrollModeFragment;
import com.example.newsandroidproject.fragment.SettingFragment;
import com.example.newsandroidproject.fragment.FavoriteFragment;
import com.example.newsandroidproject.adapter.ArticleRecycleViewAdapter;
import com.example.newsandroidproject.adapter.FavoriteViewAdapter;
import com.example.newsandroidproject.adapter.SeeLaterViewAdapter;
import com.example.newsandroidproject.fragment.*;
import com.example.newsandroidproject.databinding.ActivityMainBinding;
import com.example.newsandroidproject.model.dto.ArticleDTO;
import com.example.newsandroidproject.model.dto.NotificationDTO;
import com.example.newsandroidproject.model.dto.ResponseException;
import com.example.newsandroidproject.model.viewmodel.ArticleInReadingPageDTO;
import com.example.newsandroidproject.model.viewmodel.PostArticleRequestDTO;
import com.example.newsandroidproject.model.viewmodel.UserNavigationMenu;
import com.example.newsandroidproject.retrofit.RetrofitService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;


//import android.databinding.DataBindingUtil;



public class MainActivity extends AppCompatActivity implements ArticleRecycleViewAdapter.ArticleItemClickListener, FavoriteViewAdapter.FavoriteArticleItemClickListener, SeeLaterViewAdapter.SeeLaterArticleItemClickListener {
    private Fragment homeFragment;
    private Fragment scrollModeFragment;
    private Fragment notificationFragment;
    private Fragment settingFragment;
    private UserApi userApi;


    DrawerLayout drawerLayout;

    private ActivityMainBinding binding;

    private boolean selectedFragmentIndex = true;

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
        navigation_drawer = findViewById(R.id.navigation_drawer);
        headerView = navigation_drawer.getHeaderView(0);

        // Tìm ShapeableImageView, TextView trong header view
        ivAvarMenu = headerView.findViewById(R.id.ivAvarMenu);
        txtUsernameMenu = headerView.findViewById(R.id.txtUsernameMenu);
        txtEmailMenu = headerView.findViewById(R.id.txtEmailMenu);

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
                if (!selectedFragmentIndex) {
                    showFragment(homeFragment);
                    selectedFragmentIndex = true;
                } else {
                    ((HomeFragment)homeFragment).refreshData();
                }
            }
            if (item.getItemId() == R.id.scroll_mode_page) {
                showFragment(scrollModeFragment);
                selectedFragmentIndex = false;
            }
            if (item.getItemId() == R.id.notification_page) {
                showFragment(notificationFragment);
                selectedFragmentIndex = false;
            }
            if (item.getItemId() == R.id.setting_page) {
                showFragment(settingFragment);
                selectedFragmentIndex = false;
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


        // get notification from server
        NotificationApi apiService = RetrofitService.getClient(this).create(NotificationApi.class);
        apiService.getNotification().enqueue(new retrofit2.Callback<java.util.List<NotificationDTO>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(retrofit2.Call<java.util.List<NotificationDTO>> call, retrofit2.Response<java.util.List<NotificationDTO>> response) {
                if (response.isSuccessful()) {
                    List<NotificationDTO> notifications = response.body();
                    ((NotificationFragment)notificationFragment).setNotis(notifications);
                    Toast.makeText(MainActivity.this, "Count: " + notifications.size(), Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        ResponseException errorResponse = JsonParser.parseError(response);
//                        Toast.makeText(MainActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<java.util.List<NotificationDTO>> call, Throwable t) {
                // show error message
                Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();

            }
        });
//        startSecondActivityForResult();
        if (getIntent() != null && getIntent().hasExtra("articleID")) {
            String articleID = getIntent().getStringExtra("articleID");
            // Thực hiện các thao tác cần thiết để hiển thị HistoryFragment với articleID
        }

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


    private NavigationView navigation_drawer;
    private UserNavigationMenu userNavigationMenu;
    private View headerView;
    private ShapeableImageView ivAvarMenu;
    private TextView txtUsernameMenu, txtEmailMenu;

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

public void setOpenNavigationBar() {
    drawerLayout.openDrawer(GravityCompat.START);
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

    public void openSeeLaterFragment() {
        Fragment seeLaterFragment = new SeeLaterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, seeLaterFragment)
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
    private ArrayList<String> selectedArticleIds = new ArrayList<>();


    public void openHistoryFragmentWithSelectedIds() {
        HistoryFragment historyFragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("articleIDs", selectedArticleIds);
        historyFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, historyFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onArticleItemClick(String articleId) {
        selectedArticleIds.add(articleId);
        Toast.makeText(this, "Đã nhận articleID: " + articleId, Toast.LENGTH_SHORT).show();
    }
}