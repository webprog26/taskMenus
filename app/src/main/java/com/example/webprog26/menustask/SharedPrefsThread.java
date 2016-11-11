package com.example.webprog26.menustask;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * Created by webprog26 on 11.11.2016.
 */

class SharedPrefsThread extends HandlerThread {

    private static final String TAG = "SharedPrefsThread";

    private static final int WRITE_VISIBILITY_STATE_TO_SHARED_PREFS = 100;
    static final int READ_VISIBILITY_STATE_FROM_SHARED_PREFS = 101;

    private static final int WRITE_COLOR_TO_SHARED_PREFS = 102;
    static final int READ_COLOR_FROM_SHARED_PREFS = 103;

    static final String VIEW_VISIBILITY_PREFS_KEY = "view_visibility_prefs_key";
    static final String VIEW_VISIBILITY_PREFS_VALUE = "view_visibility_prefs_value";

    private static final String VIEW_COLOR_PREFS_KEY = "view_visibility_prefs_key";
    private static final String VIEW_COLOR_PREFS_VALUE = "view_visibility_prefs_value";

    private PreferencesUtils mPreferencesUtils;
    private Handler mUiHandler;
    private Handler mWorkerHandler;

    SharedPrefsThread(PreferencesUtils mPreferencesUtils, Handler mUiHandler) {
        super(TAG);
        this.mPreferencesUtils = mPreferencesUtils;
        this.mUiHandler = mUiHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(this.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case WRITE_VISIBILITY_STATE_TO_SHARED_PREFS:
                        mPreferencesUtils.writeToPreferences(msg.getData().getString(VIEW_VISIBILITY_PREFS_KEY), msg.getData().getBoolean(VIEW_VISIBILITY_PREFS_VALUE));
                        break;
                    case READ_VISIBILITY_STATE_FROM_SHARED_PREFS:
                        Bundle readViewStateBundle = new Bundle();
                        readViewStateBundle.putString(VIEW_VISIBILITY_PREFS_KEY, msg.getData().getString(VIEW_VISIBILITY_PREFS_KEY));
                        readViewStateBundle.putBoolean(VIEW_VISIBILITY_PREFS_VALUE, mPreferencesUtils.getVisibilityState(msg.getData().getString(VIEW_VISIBILITY_PREFS_KEY)));
                        Message readViewStateMessage = mUiHandler.obtainMessage(READ_VISIBILITY_STATE_FROM_SHARED_PREFS);
                        readViewStateMessage.setData(readViewStateBundle);
                        readViewStateMessage.sendToTarget();
                        break;
                    case WRITE_COLOR_TO_SHARED_PREFS:
                        mPreferencesUtils.writeToPreferences(msg.getData().getString(VIEW_COLOR_PREFS_KEY), msg.getData().getInt(VIEW_COLOR_PREFS_VALUE));
                        break;
                    case READ_COLOR_FROM_SHARED_PREFS:
                        Bundle readViewColorBundle = new Bundle();
                        readViewColorBundle.putString(VIEW_COLOR_PREFS_KEY, msg.getData().getString(VIEW_COLOR_PREFS_KEY));
                        readViewColorBundle.putInt(msg.getData().getString(VIEW_COLOR_PREFS_KEY), mPreferencesUtils.getColor(msg.getData().getString(VIEW_COLOR_PREFS_KEY)));
                        Message readColorMessage = mUiHandler.obtainMessage(READ_COLOR_FROM_SHARED_PREFS);
                        readColorMessage.setData(readViewColorBundle);
                        readColorMessage.sendToTarget();
                        break;

                }
            }
        };
    }

    /**
     * Sends the message to WorkerHandler to run SharedPreferences write View visibility state operation asynchronously
     * @param key {@link String}
     * @param isVisible boolean
     */
    void writeViewStateToSharedPrefs(String key, boolean isVisible){
        Bundle viewStateBundle = new Bundle();
        viewStateBundle.putString(VIEW_VISIBILITY_PREFS_KEY, key);
        viewStateBundle.putBoolean(VIEW_VISIBILITY_PREFS_VALUE, isVisible);
        Message viewStateMessage = mWorkerHandler.obtainMessage(WRITE_VISIBILITY_STATE_TO_SHARED_PREFS);
        viewStateMessage.setData(viewStateBundle);
        viewStateMessage.sendToTarget();
    }

    /**
     * Sends the message to WorkerHandler to run SharedPreferences read View visibility state operation asynchronously
     * @param key {@link String}
     */
    void readViewStateFromSharedPrefs(String key){
        Bundle viewStateBundle = new Bundle();
        viewStateBundle.putString(VIEW_VISIBILITY_PREFS_KEY, key);
        Message readStateMessage = mWorkerHandler.obtainMessage(READ_VISIBILITY_STATE_FROM_SHARED_PREFS);
        readStateMessage.setData(viewStateBundle);
        readStateMessage.sendToTarget();
    }

    /**
     * Sends the message to WorkerHandler to run SharedPreferences write View color operation asynchronously
     * @param key {@link String}
     * @param color int
     */
    void writeColorToSharedPrefs(String key, int color){
        Bundle viewColorBundle = new Bundle();
        viewColorBundle.putString(VIEW_COLOR_PREFS_KEY, key);
        viewColorBundle.putInt(VIEW_COLOR_PREFS_VALUE, color);
        Message viewColorMessage = mWorkerHandler.obtainMessage(WRITE_COLOR_TO_SHARED_PREFS);
        viewColorMessage.setData(viewColorBundle);
        viewColorMessage.sendToTarget();
    }

    /**
     * Sends the message to WorkerHandler to run SharedPreferences read View color operation asynchronously
     * @param key {@link String}
     */
    void readColorFromSharedPrefs(String key){
        Bundle viewColorBundle = new Bundle();
        viewColorBundle.putString(VIEW_COLOR_PREFS_KEY, key);
        Message readColorMessage = mWorkerHandler.obtainMessage(READ_COLOR_FROM_SHARED_PREFS);
        readColorMessage.setData(viewColorBundle);
        readColorMessage.sendToTarget();
    }

    /**
     * Avoids NPE on WorkerHandler. Read for the details https://blog.nikitaog.me/2014/10/11/android-looper-handler-handlerthread-i/
     */
    void prepareHandler(){
       if(mWorkerHandler == null){
           mWorkerHandler = new Handler(getLooper());
       }
    }
}
