package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.CalendarMonthView;
import com.defend.android.utils.ResourceManager;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthViewFragment extends Fragment {

    CalendarMonthView calendarView;
    TextView monthTextView;
    private int month, year;

    public MonthViewFragment() {
        // Required empty public constructor
    }

    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_view, container, false);

        calendarView = view.findViewById(R.id.calendar);
        calendarView.setDate(year, month);
        monthTextView = view.findViewById(R.id.month_text);
        ResourceManager.getInstance().decorateTextView(monthTextView, Color.WHITE, Constants.FONT_BOLD);
        monthTextView.setText(String.format(Locale.ENGLISH, "%d %d", year, month));

        return view;
    }

}
