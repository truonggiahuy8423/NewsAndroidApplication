package com.example.newsandroidproject.common;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimationUtil {

    public static void animate(View view, int position) {
        view.setAlpha(0f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alpha.setStartDelay(position * 100); // Delay mỗi item theo vị trí của nó
        alpha.setDuration(500);

        animatorSet.playTogether(alpha);
        animatorSet.start();
    }
}
