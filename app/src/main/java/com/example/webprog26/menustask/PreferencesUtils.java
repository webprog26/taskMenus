package com.example.webprog26.menustask;

import android.content.SharedPreferences;

/**
 * Created by webprog26 on 10.11.2016.
 */

class PreferencesUtils {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    PreferencesUtils(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
        this.mEditor = mSharedPreferences.edit();
    }

    /**
     * Writes View visibility state (true/ false) to SharedPreferences
     * @param key {@link String}
     * @param value boolean
     */
    void writeToPreferences(String key, boolean value){
        mEditor.putBoolean(key, value).commit();
    }

    /**
     * Writes view color to SharedPreferences
     * @param key {@link String}
     * @param value int
     */
    void writeToPreferences(String key, int value){
        mEditor.putInt(key, value).commit();
    }

    /**
     * Gets View visibility state (true/ false) from SharedPreferences
     * @param key
     * @return boolean
     */
    boolean getVisibilityState(String key){
        return mSharedPreferences.getBoolean(key, true);
    }

    /**
     * Gets View color from SharedPreferences
     * @param key
     * @return int
     */
    int getColor(String key){
        return mSharedPreferences.getInt(key, 0);
    }
}
