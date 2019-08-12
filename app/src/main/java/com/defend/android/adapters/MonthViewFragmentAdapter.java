package com.defend.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.defend.android.fragments.MonthViewFragment;

public class MonthViewFragmentAdapter extends FragmentPagerAdapter {
    private int count = 36;
    private int startMonth;
    private int startYear;
    public static int FIRST_PAGE = 12;

    public MonthViewFragmentAdapter(FragmentManager fm, int startYear, int startMonth) {
        super(fm);
        this.startYear = startYear;
        this.startMonth = startMonth;
    }

    @Override
    public Fragment getItem(int i) {
        MonthViewFragment fragment = new MonthViewFragment();
        fragment.setDate(getYearFromMonthAhead(i - FIRST_PAGE), getMonthFromMonthAhead(i - FIRST_PAGE));

        return fragment;
    }

    private int getYearFromMonthAhead(int deltaMonth) {
        if (deltaMonth > 0) {
            int newMonth = deltaMonth + startMonth;
            if(newMonth % 12 == 0) {
                return startYear + newMonth / 12 - 1;
            }
            return startYear + newMonth / 12;
        }
        deltaMonth = Math.abs(deltaMonth);
        int years = deltaMonth / 12;
        int year = startYear - years;
        deltaMonth = deltaMonth % 12;
        if (deltaMonth >= startMonth) year -= 1;

        return year;
    }

    private int getMonthFromMonthAhead(int deltaMonth) {
        int newMonth = startMonth + deltaMonth % 12;
        if (newMonth > 12) {
            newMonth -= 12;
        }

        if(newMonth <= 0) {
            newMonth += 12;

        }

        return newMonth;
    }

    @Override
    public int getCount() {
        return count;
    }
}
