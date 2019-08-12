package com.defend.android.calendar;

import com.defend.android.MyApp;
import com.defend.android.R;

public class CalendarUtils {
    public static String getWeekDayChar(int day) {
        if (day == 0) {
            return MyApp.getInstance().getString(R.string.fday_1);
        }

        if (day == 1) {
            return MyApp.getInstance().getString(R.string.fday_2);
        }

        if (day == 2) {
            return MyApp.getInstance().getString(R.string.fday_3);
        }

        if (day == 3) {
            return MyApp.getInstance().getString(R.string.fday_4);
        }

        if (day == 4) {
            return MyApp.getInstance().getString(R.string.fday_5);
        }

        if (day == 5) {
            return MyApp.getInstance().getString(R.string.fday_6);
        }

        if (day == 6) {
            return MyApp.getInstance().getString(R.string.fday_7);
        }

        return "";
    }
}
