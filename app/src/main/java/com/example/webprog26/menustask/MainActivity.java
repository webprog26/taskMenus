package com.example.webprog26.menustask;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";
    private View mTopView;
    private View mMidView;
    private View mBotView;
    private PreferencesUtils mPreferencesUtils;
    private ViewStateUtils mViewStateUtils;
    private View mView;


    private static final String IS_TOP_FRAGMENT_VISIBLE = "is_top_fragment_visible";
    private static final String IS_MID_FRAGMENT_VISIBLE = "is_mid_fragment_visible";
    private static final String IS_BOT_FRAGMENT_VISIBLE = "is_bot_fragment_visible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewStateUtils = new ViewStateUtils(this);

        Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.topFragmentView);
        Fragment midFragment = getSupportFragmentManager().findFragmentById(R.id.midFragmentView);
        Fragment botFragment = getSupportFragmentManager().findFragmentById(R.id.botFragmentView);

        mTopView = topFragment.getView();
        mMidView = midFragment.getView();
        mBotView = botFragment.getView();

        registerForContextMenu(mTopView);
        registerForContextMenu(mMidView);
        registerForContextMenu(mBotView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferencesUtils = new PreferencesUtils(sharedPreferences);

        mViewStateUtils.changeViewVisibilityState(mTopView, mPreferencesUtils.getVisibilityState(IS_TOP_FRAGMENT_VISIBLE));
        mViewStateUtils.changeViewVisibilityState(mMidView, mPreferencesUtils.getVisibilityState(IS_MID_FRAGMENT_VISIBLE));
        mViewStateUtils.changeViewVisibilityState(mBotView, mPreferencesUtils.getVisibilityState(IS_BOT_FRAGMENT_VISIBLE));

        ViewStateUtils.changeViewBackgroundColor(mTopView, mPreferencesUtils.getColor(String.valueOf(mTopView.getId())));
        ViewStateUtils.changeViewBackgroundColor(mMidView, mPreferencesUtils.getColor(String.valueOf(mMidView.getId())));
        ViewStateUtils.changeViewBackgroundColor(mBotView, mPreferencesUtils.getColor(String.valueOf(mBotView.getId())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.topViewAction:
                    item.setChecked(!item.isChecked());
                    mPreferencesUtils.writeToPreferences(IS_TOP_FRAGMENT_VISIBLE, item.isChecked());
                    mViewStateUtils.changeViewVisibilityState(mTopView, item.isChecked());
                    return true;
                case R.id.middleViewAction:
                    item.setChecked(!item.isChecked());
                    mPreferencesUtils.writeToPreferences(IS_MID_FRAGMENT_VISIBLE, item.isChecked());
                    mViewStateUtils.changeViewVisibilityState(mMidView, item.isChecked());
                    return true;
                case R.id.bottomViewAction:
                    item.setChecked(!item.isChecked());
                    mPreferencesUtils.writeToPreferences(IS_BOT_FRAGMENT_VISIBLE, item.isChecked());
                    mViewStateUtils.changeViewVisibilityState(mBotView, item.isChecked());
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setChecked(mPreferencesUtils.getVisibilityState(IS_TOP_FRAGMENT_VISIBLE));
        menu.getItem(1).setChecked(mPreferencesUtils.getVisibilityState(IS_MID_FRAGMENT_VISIBLE));
        menu.getItem(2).setChecked(mPreferencesUtils.getVisibilityState(IS_BOT_FRAGMENT_VISIBLE));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
        mView = v;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int color;
        switch (item.getItemId()){
            case R.id.colorAccentAction:
                    color = getResources().getColor(R.color.colorAccent);
                    ViewStateUtils.changeViewBackgroundColor(mView, color);
                    mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                    return true;
            case R.id.colorSkyAction:
                color =  getResources().getColor(R.color.colorSky);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorBlueAction:
                color =  getResources().getColor(R.color.colorPrimary);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorRedAction:
                color =  getResources().getColor(R.color.colorRed);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorGreenAction:
                color = getResources().getColor(R.color.colorGreen);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorOrangeAction:
                color =  getResources().getColor(R.color.colorOrange);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mPreferencesUtils.writeToPreferences(String.valueOf(mView.getId()), color);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
