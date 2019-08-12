package com.defend.android.customViews;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.defend.android.R;
import com.defend.android.adapters.CalendarMonthAdapter;

public class CalendarMonthView extends RelativeLayout {

    RecyclerView recyclerView;
    private int month; //Jalali

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

    private void initView() {
        View view = inflate(getContext(), R.layout.calendar_month, null);
        addView(view);

        recyclerView = view.findViewById(R.id.main_recycler);
    }

    public void setMonth(int month) {
        this.month = month;
        initRecycler();
    }

    CalendarMonthAdapter adapter;
    private void initRecycler() {
        adapter = new CalendarMonthAdapter();
        adapter.setMonth(month);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);
    }
}
