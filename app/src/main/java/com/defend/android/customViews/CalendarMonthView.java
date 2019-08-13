package com.defend.android.customViews;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.defend.android.R;
import com.defend.android.adapters.CalendarMonthAdapter;
import com.defend.android.data.Event;
import com.defend.android.listeners.CalendarOnDaySelectListener;

import java.util.ArrayList;

public class CalendarMonthView extends RelativeLayout {

    RecyclerView recyclerView;
    private int month; //Jalali
    private int year;
    CalendarOnDaySelectListener listener;

    public CalendarMonthView(Context context) {
        super(context);
        initView();
    }

    public CalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CalendarMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setListener(CalendarOnDaySelectListener listener) {
        this.listener = listener;
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.calendar_month, null);
        addView(view);

        recyclerView = view.findViewById(R.id.main_recycler);
    }

    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
        initRecycler();
    }

    CalendarMonthAdapter adapter;
    private void initRecycler() {
        adapter = new CalendarMonthAdapter();
        adapter.setDate(year, month);
        adapter.setListener(listener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);
    }

    public void setEvents(ArrayList<Event> events) {
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }
}
