package com.defend.android.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.defend.android.R;

public class CalendarMonthView extends RelativeLayout {
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
    }
}
