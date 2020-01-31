package com.defend.android.activites;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.defend.android.R;
import com.defend.android.customViews.ActivityToolbar;

public class IdeaInfoActivity extends Activity {

    ActivityToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_info);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.my_idea_info_title));
    }
}
