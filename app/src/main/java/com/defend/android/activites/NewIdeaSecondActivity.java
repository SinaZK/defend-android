package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.customViews.IdeaCategoryDialog;
import com.defend.android.data.IdeaHelper;
import com.defend.android.utils.ResourceManager;

public class NewIdeaSecondActivity extends Activity {

    ActivityToolbar toolbar;
    EditText titleEditText, bodyEditText;
    TextView categoryTextView;
    Button submitButton;


    IdeaCategoryDialog.onSelectListener listener = new IdeaCategoryDialog.onSelectListener() {
        @Override
        public void onSelect(String text) {
            categoryTextView.setText(text);
            IdeaHelper.getInstance().getIdea().setCategory(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_second);

        toolbar = findViewById(R.id.toolbar);
        titleEditText = findViewById(R.id.titleET);
        bodyEditText = findViewById(R.id.bodyET);
        categoryTextView = findViewById(R.id.categoryTV);
        submitButton = findViewById(R.id.submit);

        initUI();
        fillUI();
    }

    private void initUI() {
        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.idea_activity_title));

        ResourceManager.getInstance().decorateEditText(titleEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(bodyEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(categoryTextView, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateButton(submitButton, Color.WHITE);
        submitButton.setBackgroundResource(R.drawable.btn_bg);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdeaCategoryDialog dialog = new IdeaCategoryDialog(NewIdeaSecondActivity.this);
                dialog.setListener(listener);
                dialog.show();
            }
        });
    }

    private void updateIdea() {
        IdeaHelper.getInstance().getIdea().setTitle(titleEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setBody(bodyEditText.getText().toString());
    }

    private void fillUI() {
        titleEditText.setText(IdeaHelper.getInstance().getIdea().getTitle());
        bodyEditText.setText(IdeaHelper.getInstance().getIdea().getBody());
        if (!IdeaHelper.getInstance().getIdea().getCategory().equals("")) {
            categoryTextView.setText(IdeaHelper.getInstance().getIdea().getCategory());
        }
    }

    private void sendRequest() {
        updateIdea();
    }

    private void setProgress(boolean loading) {

    }
}
