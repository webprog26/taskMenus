package com.example.webprog26.menustask;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by webprog26 on 10.11.2016.
 */

public class ViewStateUtils {

    private Activity mActivity;
    private Animation mAnimationIn;
    private Animation mAnimationOut;


    public ViewStateUtils(Activity mActivity) {
        this.mActivity = mActivity;
        mAnimationIn = AnimationUtils.loadAnimation(mActivity, R.anim.combination_in);
        mAnimationOut = AnimationUtils.loadAnimation(mActivity, R.anim.combination_out);
    }

    public void changeViewVisibilityState(View view, boolean isVisible){
        if(isVisible){
            view.startAnimation(mAnimationIn);
            view.setVisibility(View.VISIBLE);
        } else {
            view.startAnimation(mAnimationOut);
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void changeViewBackgroundColor(View view, int color)
    {
        if(color != 0){
            view.setBackgroundColor(color);
        }
    }
}
