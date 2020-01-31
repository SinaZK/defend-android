package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.customViews.IdeaCategoryDialog;
import com.defend.android.data.IdeaHelper;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;

import org.json.JSONObject;

public class NewIdeaSecondActivity extends Activity {

    ActivityToolbar toolbar;
    EditText titleEditText, bodyEditText;
    TextView categoryTextView;
    Button submitButton;
    ProgressBar progressBar;


    IdeaCategoryDialog.onSelectListener listener = new IdeaCategoryDialog.onSelectListener() {
        @Override
        public void onSelect(String text) {
            categoryTextView.setError(null);
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
        progressBar = findViewById(R.id.progress);

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
                sendRequest();
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
        if (!isOkFields()) {
            return;
        }
        updateIdea();

        String url = Constants.API_URL + Constants.API_IDEA_CREATE;
        JSONObject object = IdeaHelper.getInstance().getIdea().toJson();

        setProgress(true);

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                Utils.showToast(getString(R.string.idea_activity_ok_toast));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
            }
        });

        NetworkManager.getInstance().sendRequest(request);
    }

    private boolean isOkFields() {
        if (IdeaHelper.getInstance().getIdea().getCategory().equals("")) {
            categoryTextView.setError("");
            return false;
        }

        return true;
    }

    private void setProgress(boolean loading) {
        if(loading) {
            progressBar.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
        }
    }
}
