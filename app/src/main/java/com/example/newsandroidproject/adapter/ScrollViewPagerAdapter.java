package com.example.newsandroidproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.newsandroidproject.fragment.ScrollExploreFragment;
import com.example.newsandroidproject.fragment.ScrollFollowedFragment;

public class ScrollViewPagerAdapter extends FragmentStateAdapter {
    public ScrollViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ScrollFollowedFragment();
            case 1:
                return new ScrollExploreFragment();
            default:
                return new ScrollFollowedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
