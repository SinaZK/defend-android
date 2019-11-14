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
import com.defend.android.utils.ResourceManager;

public class MyToolbar extends RelativeLayout {
    MainActivity activity;
    TextView textView;
    ImageView drawerButton;


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
        textView = view.findViewById(R.id.text);

        ResourceManager.getInstance().decorateTextView(textView, Color.WHITE);
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

    public void setText(String text) {
        textView.setText(text);
    }
}
