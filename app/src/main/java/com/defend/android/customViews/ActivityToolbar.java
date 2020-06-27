package com.defend.android.customViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.utils.ResourceManager;

public class ActivityToolbar extends RelativeLayout {
    Activity activity;
    TextView textView;
    ImageView backButton;


    public ActivityToolbar(Context context) {
        super(context);
        initView();
    }

    public ActivityToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ActivityToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.activity_toolbar, null);
        addView(view);

        backButton = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.text);

        ResourceManager.getInstance().decorateTextView(textView, Color.BLACK);
    }

    public void setActivity(final Activity activity) {
        this.activity = activity;

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
