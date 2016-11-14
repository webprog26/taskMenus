package com.example.webprog26.menustask;

import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by webprog26 on 11.11.2016.
 */

class UiHandler extends Handler{

    private View mTopView;
    private View mMidView;
    private View mBotView;
    private ViewStateUtils mViewStateUtils;

    UiHandler(View mTopView, View mMidView, View mBotView, ViewStateUtils mViewStateUtils) {
        this.mTopView = mTopView;
        this.mMidView = mMidView;
        this.mBotView = mBotView;
        this.mViewStateUtils = mViewStateUtils;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case SharedPrefsThread.READ_VISIBILITY_STATE_FROM_SHARED_PREFS:
                mViewStateUtils.changeViewVisibilityState(getCurrentView(msg.getData().getString(SharedPrefsThread.VIEW_VISIBILITY_PREFS_KEY)),
                        msg.getData().getBoolean(SharedPrefsThread.VIEW_VISIBILITY_PREFS_VALUE));
                break;
            case SharedPrefsThread.READ_COLOR_FROM_SHARED_PREFS:
                ViewStateUtils.changeViewBackgroundColor(mTopView, msg.getData().getInt(String.valueOf(mTopView.getId())));
                ViewStateUtils.changeViewBackgroundColor(mMidView, msg.getData().getInt(String.valueOf(mMidView.getId())));
                ViewStateUtils.changeViewBackgroundColor(mBotView, msg.getData().getInt(String.valueOf(mBotView.getId())));
                break;

        }
    }

    /**
     * Returns one of three Views bawsed on given as a parameter {@link String} ID
     * @param key {@link String}
     * @return {@link View}
     */
    private View getCurrentView(String key){
        switch (key){
            case MainActivity.IS_TOP_FRAGMENT_VISIBLE:
                return mTopView;
            case MainActivity.IS_MID_FRAGMENT_VISIBLE:
                return mMidView;
            case MainActivity.IS_BOT_FRAGMENT_VISIBLE:
                return mBotView;
            default:
                return null;
        }
    }
}
