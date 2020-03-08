package com.defend.android.customViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.constants.Constants;
import com.defend.android.listeners.SearchListener;
import com.defend.android.utils.ResourceManager;

public class SearchToolbar extends RelativeLayout {
    MainActivity activity;
    EditText searchEditText;
    ImageView drawerButton;
    ImageView searchImage;
    SearchListener searchListener;


    public SearchToolbar(Context context) {
        super(context);
        initView();
    }

    public SearchToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.search_toolbar, null);
        addView(view);

        drawerButton = view.findViewById(R.id.button);
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchImage = view.findViewById(R.id.search_icon);

        ResourceManager.getInstance().decorateTextView(searchEditText, Color.WHITE);
        ResourceManager.getInstance().decorateEditText(searchEditText, Color.WHITE, Constants.FONT_REGULAR);

        searchImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSearch();
                    return false;
                }

                return false;
            }
        });
    }

    public void hideDrawerButton() {
        drawerButton.setVisibility(GONE);
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

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    private void onSearch() {
        if (searchListener != null) {
            searchListener.onSearch(searchEditText.getText().toString());
        }
    }

    public void setText(String text) {
        searchEditText.setText(text);
    }

    public void setFocus() {
        searchEditText.requestFocus();
    }
}
