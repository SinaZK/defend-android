package com.defend.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.defend.android.fragments.MonthViewFragment;

public class MonthViewFragmentAdapter extends FragmentPagerAdapter {
    private int count = 24;
    private int startMonth = 1;
    private int startYear = 1398;

    public MonthViewFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        MonthViewFragment fragment = new MonthViewFragment();
        fragment.setDate(getYearFromMonthAhead(i), getMonthFromMonthAhead(i));

        return fragment;
    }

    private int getYearFromMonthAhead(int deltaMonth) {
        return startYear + deltaMonth / 12;
    }

    private int getMonthFromMonthAhead(int deltaMonth) {
        int newMonth = startMonth + deltaMonth % 12;
        if (newMonth > 12) {
            newMonth -= 12;
        }

        return newMonth;
    }

    @Override
    public int getCount() {
        return count;
    }
}
