package com.defend.android.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.defend.android.MyApp;
import com.defend.android.constants.Constants;

public class DataStore {
    private static DataStore instance;

    public boolean showAddMemberDialog = true;
    public boolean showAddMemberMoneypoolDialog = true;
    private SharedPreferences sharedPref;

    private DataStore() {
        sharedPref = MyApp.getInstance().getSharedPreferences(MyApp.getInstance().getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static DataStore getInstance() {
        if (instance == null)
            instance = new DataStore();

        return instance;
    }

    private SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public String getStringData(String key, String defaultValue) {
        return getSharedPref().getString(key, defaultValue);
    }

    public boolean getBooleanData(String value, boolean defaultValue) {
        return getSharedPref().getBoolean(value, defaultValue);
    }

    public int getIntData(String key, int defaultValue) {
        return getSharedPref().getInt(key, defaultValue);
    }

    public float getFloatData(String key, float defaultValue) {
        return getSharedPref().getFloat(key, defaultValue);
    }

    public boolean setStringData(String key, String value) {
        return getSharedPref().edit().putString(key, value).commit();
    }

    public boolean setIntData(String key, int value) {
        return getSharedPref().edit().putInt(key, value).commit();
    }

    public boolean setFloatData(String key, float value) {
        return getSharedPref().edit().putFloat(key, value).commit();
    }

    public boolean setBooleanData(String key, boolean value) {
        return getSharedPref().edit().putBoolean(key, value).commit();
    }

    public boolean isLogin() {
        return getStringData(Constants.DATA_FIELD_TOKEN, "").length() > 0;
    }
}
