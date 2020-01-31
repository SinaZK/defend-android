package com.defend.android.activites;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defend.android.R;
import com.defend.android.customViews.ActivityToolbar;

public class NewIdeaFirstActivity extends Activity {

    ActivityToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_first);

        toolbar = findViewById(R.id.toolbar);
    }
}
