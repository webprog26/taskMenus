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
    private View mView;
    private SharedPrefsThread mSharedPrefsThread;
    private UiHandler mUiHandler;


    public static final String IS_TOP_FRAGMENT_VISIBLE = "is_top_fragment_visible";
    public static final String IS_MID_FRAGMENT_VISIBLE = "is_mid_fragment_visible";
    public static final String IS_BOT_FRAGMENT_VISIBLE = "is_bot_fragment_visible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragments initializing
        Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.topFragmentView);
        Fragment midFragment = getSupportFragmentManager().findFragmentById(R.id.midFragmentView);
        Fragment botFragment = getSupportFragmentManager().findFragmentById(R.id.botFragmentView);

        //Views from Fragments
        mTopView = topFragment.getView();
        mMidView = midFragment.getView();
        mBotView = botFragment.getView();

        //Registering view for handling onTouch events, showing context menu
        registerForContextMenu(mTopView);
        registerForContextMenu(mMidView);
        registerForContextMenu(mBotView);

        //SharedPreferences initializing
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Separate class to run preferences operations
        PreferencesUtils mPreferencesUtils = new PreferencesUtils(sharedPreferences);

        //UI Handler to run operations with the views
        mUiHandler = new UiHandler(mTopView, mMidView, mBotView, new ViewStateUtils(this));

        //Separate HandlerThread to run SharedPreferences write/read operations asynchronously
        mSharedPrefsThread = new SharedPrefsThread(mPreferencesUtils, mUiHandler);
        mSharedPrefsThread.start();
        mSharedPrefsThread.prepareHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Getting views visibility state from SharedPreferences
        mSharedPrefsThread.readViewStateFromSharedPrefs(IS_TOP_FRAGMENT_VISIBLE);
        mSharedPrefsThread.readViewStateFromSharedPrefs(IS_MID_FRAGMENT_VISIBLE);
        mSharedPrefsThread.readViewStateFromSharedPrefs(IS_BOT_FRAGMENT_VISIBLE);

        //Getting views colors state from SharedPreferences
        mSharedPrefsThread.readColorFromSharedPrefs(String.valueOf(mTopView.getId()));
        mSharedPrefsThread.readColorFromSharedPrefs(String.valueOf(mMidView.getId()));
        mSharedPrefsThread.readColorFromSharedPrefs(String.valueOf(mBotView.getId()));
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
                    mSharedPrefsThread.writeViewStateToSharedPrefs(IS_TOP_FRAGMENT_VISIBLE, item.isChecked());
                    new ViewStateUtils(this).changeViewVisibilityState(mTopView, item.isChecked());
                    return true;
                case R.id.middleViewAction:
                    item.setChecked(!item.isChecked());
                    mSharedPrefsThread.writeViewStateToSharedPrefs(IS_MID_FRAGMENT_VISIBLE, item.isChecked());
                    new ViewStateUtils(this).changeViewVisibilityState(mMidView, item.isChecked());
                    return true;
                case R.id.bottomViewAction:
                    item.setChecked(!item.isChecked());
                    mSharedPrefsThread.writeViewStateToSharedPrefs(IS_BOT_FRAGMENT_VISIBLE, item.isChecked());
                    new ViewStateUtils(this).changeViewVisibilityState(mBotView, item.isChecked());
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setChecked(mTopView.getVisibility()!= View.INVISIBLE);
        menu.getItem(1).setChecked(mMidView.getVisibility()!= View.INVISIBLE);
        menu.getItem(2).setChecked(mBotView.getVisibility()!= View.INVISIBLE);

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
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorSkyAction:
                color =  getResources().getColor(R.color.colorSky);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorBlueAction:
                color = getResources().getColor(R.color.colorPrimary);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorRedAction:
                color =  getResources().getColor(R.color.colorRed);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorGreenAction:
                color = getResources().getColor(R.color.colorGreen);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
            case R.id.colorOrangeAction:
                color =  getResources().getColor(R.color.colorOrange);
                ViewStateUtils.changeViewBackgroundColor(mView, color);
                mSharedPrefsThread.writeColorToSharedPrefs(String.valueOf(mView.getId()), color);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPrefsThread.quit();
    }
}
