package com.example.newsandroidproject.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import android.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.newsandroidproject.ui.fragments.HomeFragment;
import com.example.newsandroidproject.ui.fragments.NotificationFragment;
import com.example.newsandroidproject.R;
import com.example.newsandroidproject.ui.fragments.ScrollModeFragment;
import com.example.newsandroidproject.ui.fragments.SettingFragment;
import com.example.newsandroidproject.databinding.ActivityMainBinding;


//import android.databinding.DataBindingUtil;



public class MainActivity extends AppCompatActivity {
    private Fragment homeFragment;
    private Fragment scrollModeFragment;
    private Fragment notificationFragment;
    private Fragment settingFragment;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        scrollModeFragment = new ScrollModeFragment();
        notificationFragment = new NotificationFragment();
        settingFragment = new SettingFragment();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeFragment(homeFragment);

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
        ft.commit();
    }
}