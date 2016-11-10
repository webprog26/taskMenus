package com.example.webprog26.menustask;

import android.content.SharedPreferences;

/**
 * Created by webprog26 on 10.11.2016.
 */

public class PreferencesUtils {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PreferencesUtils(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
        this.mEditor = mSharedPreferences.edit();
    }

    public void writeToPreferences(String key, boolean value){
        mEditor.putBoolean(key, value).commit();
    }

    public void writeToPreferences(String key, int value){
        mEditor.putInt(key, value).commit();
    }

    public boolean getVisibilityState(String key){
        return mSharedPreferences.getBoolean(key, true);
    }

    public int getColor(String key){
        return mSharedPreferences.getInt(key, 0);
    }
}
