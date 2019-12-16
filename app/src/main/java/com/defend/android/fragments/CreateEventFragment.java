package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {

    MainActivity activity;
    EditText titleEditText, locationEditText, bodyEditText;
    TextView timeTextView, dateTextView;
    Button submitButton;
    ProgressBar progressBar;


    public CreateEventFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        titleEditText = view.findViewById(R.id.titleTV);
        locationEditText = view.findViewById(R.id.locationTV);
        bodyEditText = view.findViewById(R.id.bodyTV);
        timeTextView = view.findViewById(R.id.time_tv);
        dateTextView = view.findViewById(R.id.date_tv);
        submitButton = view.findViewById(R.id.submit);
        progressBar = view.findViewById(R.id.progress);

        initUI();

        return view;
    }

    private void initUI() {
        ResourceManager.getInstance().decorateEditText(titleEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(locationEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(bodyEditText, Color.WHITE, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(timeTextView, Color.WHITE, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(dateTextView, Color.WHITE, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateButton(submitButton, Color.WHITE, Constants.FONT_REGULAR);

        submitButton.setBackgroundResource(R.drawable.btn_bg);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void sendSubmitRequest() {

    }

    private void onSuccess() {

    }

}
