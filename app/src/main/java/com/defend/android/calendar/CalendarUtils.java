package com.defend.android.calendar;

import android.util.Log;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.time.LocalDate;
import java.util.Calendar;

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

    public static String getMonthPersianString(int month) {
        switch (month) {
            case 1:
                return MyApp.getInstance().getString(R.string.month_1);
            case 2:
                return MyApp.getInstance().getString(R.string.month_2);
            case 3:
                return MyApp.getInstance().getString(R.string.month_3);
            case 4:
                return MyApp.getInstance().getString(R.string.month_4);
            case 5:
                return MyApp.getInstance().getString(R.string.month_5);
            case 6:
                return MyApp.getInstance().getString(R.string.month_6);
            case 7:
                return MyApp.getInstance().getString(R.string.month_7);
            case 8:
                return MyApp.getInstance().getString(R.string.month_8);
            case 9:
                return MyApp.getInstance().getString(R.string.month_9);
            case 10:
                return MyApp.getInstance().getString(R.string.month_10);
            case 11:
                return MyApp.getInstance().getString(R.string.month_11);
            case 12:
                return MyApp.getInstance().getString(R.string.month_12);
        }

        return "";
    }


    public static int getFirstWeekDayOfMonth(int year, int month) {
        PersianCalendar calendar = new PersianCalendar();
        calendar.setPersianDate(year, month - 1, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) % 7;
    }

    public static int getDaysInMonth(int year, int month) {
        if (month <= 6) return 31;
        if (month < 12) return 30;

        JalaliDate date = new JalaliDate(year, month, 1);
        if (date.isLeapYear()) {
            return 30;
        }

        return 29;
    }

}
