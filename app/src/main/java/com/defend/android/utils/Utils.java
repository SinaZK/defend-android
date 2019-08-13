package com.defend.android.utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.Toast;

import com.defend.android.MyApp;

public class Utils {
    public static void showToast(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_LONG).show();
    }

    public static int dpToPx(float dip) {
        Resources r = MyApp.getInstance().getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

        return (int)px;
    }
}
