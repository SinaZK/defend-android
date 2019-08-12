package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defend.android.R;
import com.defend.android.adapters.MonthViewFragmentAdapter;
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.customViews.CalendarMonthView;
import com.defend.android.customViews.CalendarView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    ViewPager viewPager;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        for(int i = 1;i <= 12;i++) {
            Log.i("salam", "" + i + " :" + CalendarUtils.getFirstWeekDayOfMonth(1398, i));
        }

        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new MonthViewFragmentAdapter(getChildFragmentManager()));

        return view;
    }

}
