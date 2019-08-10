package com.defend.android;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application {
    private static MyApp singleton;

    public static MyApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        Log.i("_MAIN", "THIS CALLED");
    }
}
