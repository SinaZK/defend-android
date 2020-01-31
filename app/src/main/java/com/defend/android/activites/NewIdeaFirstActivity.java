package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.customViews.EducationDialog;
import com.defend.android.data.Idea;
import com.defend.android.data.IdeaHelper;
import com.defend.android.utils.ResourceManager;

public class NewIdeaFirstActivity extends Activity {

    private String TAG = "_idea";

    ActivityToolbar toolbar;
    EditText nameEditText, locationEditText, fieldEditText, phoneEditText, emailEditText, addressEditText;
    TextView educationTextView;
    Button nextButton;

    private EducationDialog.onSelectListener listener = new EducationDialog.onSelectListener() {
        @Override
        public void onSelect(String education, String text) {
            IdeaHelper.getInstance().getIdea().setEducation(education);
            setEducationText(education);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea_first);

        toolbar = findViewById(R.id.toolbar);
        nameEditText = findViewById(R.id.nameET);
        locationEditText = findViewById(R.id.locationET);
        fieldEditText = findViewById(R.id.educationFieldET);
        phoneEditText = findViewById(R.id.phoneET);
        emailEditText = findViewById(R.id.emailET);
        addressEditText = findViewById(R.id.addressET);
        educationTextView = findViewById(R.id.educationTV);
        nextButton = findViewById(R.id.submit);

        initUI();
        fillUI();
    }

    private void initUI() {
        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.idea_activity_title));

        ResourceManager.getInstance().decorateEditText(nameEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(locationEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(fieldEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(phoneEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(emailEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(addressEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(educationTextView, Color.WHITE);

        ResourceManager.getInstance().decorateButton(nextButton, Color.WHITE);
        nextButton.setBackgroundResource(R.drawable.btn_bg);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIdea();
                finish();
            }
        });

        educationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EducationDialog dialog = new EducationDialog(NewIdeaFirstActivity.this);
                dialog.setListener(listener);
                dialog.show();
            }
        });
    }

    private void fillUI() {
        nameEditText.setText(IdeaHelper.getInstance().getIdea().getName());
        locationEditText.setText(IdeaHelper.getInstance().getIdea().getLocation());
        fieldEditText.setText(IdeaHelper.getInstance().getIdea().getField());
        phoneEditText.setText(IdeaHelper.getInstance().getIdea().getPhone());
        emailEditText.setText(IdeaHelper.getInstance().getIdea().getEmail());
        addressEditText.setText(IdeaHelper.getInstance().getIdea().getAddress());
        setEducationText(IdeaHelper.getInstance().getIdea().getEducation());
    }

    private void updateIdea() {
        IdeaHelper.getInstance().getIdea().setName(nameEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setLocation(locationEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setField(fieldEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setPhone(phoneEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setEmail(emailEditText.getText().toString());
        IdeaHelper.getInstance().getIdea().setAddress(addressEditText.getText().toString());
    }

    private void setEducationText(String education) {
        if (education.equals("")) return;

        String text = "";

        if (education.equals("diploma")) {
            text = getString(R.string.education_diploma);
        }

        if (education.equals("bachelour")) {
            text = getString(R.string.education_bachelour);
        }

        if (education.equals("master")) {
            text = getString(R.string.education_master);
        }

        if (education.equals("doc")) {
            text = getString(R.string.education_doc);
        }

        if (education.equals("post-doc")) {
            text = getString(R.string.education_post_doc);
        }

        educationTextView.setText(text);
    }
}
