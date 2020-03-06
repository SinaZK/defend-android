package com.defend.android.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aditya.filebrowser.FileChooser;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.AuthSimpleMultipartRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.Network.VolleyMultipartRequest;
import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.constants.Constants;
import com.defend.android.data.DataStore;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    MainActivity activity;
    EditText titleEditText, locationEditText, bodyEditText;
    TextView timeTextView, dateTextView, fileTextView;
    Button submitButton;
    ProgressBar progressBar;
    Uri selectedFile = null;

    private int hour = 12;
    private int minute = 0;
    private int day = 1;
    private int month = 1;
    private int year = 1396;


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
        fileTextView = view.findViewById(R.id.fileET);
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
                sendSubmitRequest();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = TimePickerDialog.newInstance(CreateEventFragment.this,
                        hour, minute, true);
                dialog.show(getFragmentManager(), "timePicker");
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(CreateEventFragment.this,
                        year, month, day);
                dialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        fileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FileChooser.class);
                intent.putExtra(com.aditya.filebrowser.Constants.SELECTION_MODE, com.aditya.filebrowser.Constants.SELECTION_MODES.SINGLE_SELECTION.ordinal());
                startActivityForResult(intent, Constants.FILE_PICKER_REQUEST);
            }
        });

        PersianCalendar calendar = new PersianCalendar();

        updateTime(hour, minute);
        updateDate(calendar.getPersianYear(), calendar.getPersianMonth(), calendar.getPersianDay());
    }

    private void updateTime(int h, int m) {
        hour = h;
        minute = m;
        timeTextView.setText(String.format(Locale.ENGLISH, getString(R.string.time_tv), hour, minute));
    }

    private void updateDate(int y, int m, int d) {
        m = m + 1;
        year = y;
        month = m;
        day = d;
        dateTextView.setText(String.format(Locale.ENGLISH, getString(R.string.date_tv), day,
                CalendarUtils.getMonthPersianString(month), year));
    }

    private void setProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
        }
    }

    private void sendSubmitRequest() {
        String url = Constants.API_URL + Constants.API_SUBMIT_NEW_EVENT;

        JSONObject object = new JSONObject();

        try {
            object.put("title", titleEditText.getText().toString());
            object.put("body", bodyEditText.getText().toString());
            object.put("location", locationEditText.getText().toString());
            object.put("date", getGregorianDateString());
            object.put("time", getTimeString());
        } catch (Exception e) {
            Log.i("_Create", e.toString());
        }

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                setProgress(false);
                Log.i("_Create", "Success: " + response);
                onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", titleEditText.getText().toString());
                params.put("body", bodyEditText.getText().toString());
                params.put("location", locationEditText.getText().toString());
                params.put("date", getGregorianDateString());
                params.put("time", getTimeString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();

                if (selectedFile != null) {
                    params.put("image", new DataPart("image.jpg", Utils.getFileDataFromUri(activity, selectedFile)));
                }

                return params;
            }
        };

        setProgress(true);

        NetworkManager.getInstance().sendRequest(request);
    }

    private void onSuccess() {
        Utils.showToast(getString(R.string.success_toast));
        activity.setFragment(Constants.MENU_HOME);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        updateDate(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        updateTime(hourOfDay, minute);
    }

    private String getGregorianDateString() {
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(year, month - 1, day);
        return "" + persianCalendar.get(Calendar.YEAR) + "-" + (persianCalendar.get(Calendar.MONTH) + 1) + "-" +
                persianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private String getTimeString() {
        return "" + hour + ":" + minute;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.FILE_PICKER_REQUEST) {
            if (data == null) {
                selectedFile = null;

                return;
            }

            selectedFile = data.getData();
            if (selectedFile != null){
//                Log.i("_event", selectedFile.toString());
                fileTextView.setText(Utils.getFileNameFromUri(selectedFile));
            }
        }
    }
}
