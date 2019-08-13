package com.defend.android.customViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.defend.android.utils.Utils;

public class ExtendedRecyclerView extends RecyclerView {
    public ExtendedRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ExtendedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(Utils.dpToPx(240), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
