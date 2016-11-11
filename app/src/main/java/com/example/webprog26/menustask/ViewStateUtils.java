package com.example.webprog26.menustask;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by webprog26 on 10.11.2016.
 */

class ViewStateUtils {

    private Animation mAnimationIn;
    private Animation mAnimationOut;

    private static final int ERROR_WHILE_GETTING_VIEW_BACKGROUND_COLOR = 0;

    ViewStateUtils(Activity activity) {
        mAnimationIn = AnimationUtils.loadAnimation(activity, R.anim.combination_in);
        mAnimationOut = AnimationUtils.loadAnimation(activity, R.anim.combination_out);
    }

    /**
     * Changes View visibility using animation (rotate, scale, translate)
     * @param view {@link View}
     * @param isVisible boolean
     */
    void changeViewVisibilityState(View view, boolean isVisible){
        if(isVisible){
            view.startAnimation(mAnimationIn);
            view.setVisibility(View.VISIBLE);
        } else {
            view.startAnimation(mAnimationOut);
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Changes background color of the {@link View}
     * @param view {@link View}
     * @param color int
     */
    static void changeViewBackgroundColor(View view, int color)
    {
        if(color != ERROR_WHILE_GETTING_VIEW_BACKGROUND_COLOR){
            view.setBackgroundColor(color);
        }
    }
}
