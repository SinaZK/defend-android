package com.defend.android;

import android.app.Application;
import android.util.Log;

import com.defend.android.data.DataStore;
import com.defend.android.download.CustomDownloadManager;

public class MyApp extends Application {
    private static MyApp singleton;

    public static MyApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        DataStore.getInstance();
        CustomDownloadManager.getInstance().register();
    }
}
