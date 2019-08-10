package com.defend.android.utils;

import android.widget.Toast;

import com.defend.android.MyApp;

public class Utils {
    public static void showToast(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
