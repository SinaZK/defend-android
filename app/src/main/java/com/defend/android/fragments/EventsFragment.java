package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defend.android.R;
import com.defend.android.customViews.CalendarMonthView;
import com.defend.android.customViews.CalendarView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    CalendarMonthView calendarView;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        calendarView = view.findViewById(R.id.calendar);
        calendarView.setMonth(1);

        return view;
    }

}
