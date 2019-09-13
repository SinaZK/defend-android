package com.defend.android.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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

    public static void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApp.getInstance().startActivity(browserIntent);
    }
}
