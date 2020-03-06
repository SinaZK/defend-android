package com.defend.android.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.adapters.MonthViewFragmentAdapter;
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.calendar.DateConverter;
import com.defend.android.calendar.JalaliDate;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.CalendarMonthView;
import com.defend.android.customViews.CalendarView;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    MainActivity activity;
    ViewPager viewPager;
    FloatingActionButton button;

    public EventsFragment() {
        // Required empty public constructor
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        button = view.findViewById(R.id.add_btn);

        DateConverter converter = new DateConverter();
        JalaliDate today = converter.nowAsJalali();
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new MonthViewFragmentAdapter(getChildFragmentManager(), today.getYear(), today.getMonthPersian().getValue()));
        viewPager.setCurrentItem(MonthViewFragmentAdapter.FIRST_PAGE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragment(Constants.MENU_NEWEVENT);
            }
        });

        return view;
    }

}
