package com.defend.android.customViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

public class MyToolbar extends RelativeLayout {
    MainActivity activity;
    TextView textView;
    ImageView drawerButton;
    ImageView searchButton;

    Runnable searchRunnable;


    public MyToolbar(Context context) {
        super(context);
        initView();
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.my_toolbar, null);
        addView(view);

        drawerButton = view.findViewById(R.id.button);
        searchButton = view.findViewById(R.id.search);
        textView = view.findViewById(R.id.text);

        ResourceManager.getInstance().decorateTextView(textView, Color.BLACK, Constants.FONT_BOLD);

        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchRunnable != null) {
                    searchRunnable.run();
                }
            }
        });
    }

    public void setActivity(final MainActivity activity) {
        this.activity = activity;

        drawerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.drawer.isDrawerOpen()) {
                    activity.drawer.closeDrawer();
                } else {
                    activity.drawer.openDrawer();
                }
            }
        });
    }

    public void setOnSearch(Runnable runnable) {
        this.searchRunnable = runnable;
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void hideSearch() {
        searchButton.setVisibility(GONE);
    }

    public void showSearch() {
        searchButton.setVisibility(VISIBLE);
    }
}
