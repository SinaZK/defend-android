package com.defend.android.activites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Event;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class EventDetailActivity extends Activity {

    TextView title, body, date, location, student, teacher, university, time;
    ImageView image;
    Event event = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        image = findViewById(R.id.image);
        university = findViewById(R.id.universityTV);
        student = findViewById(R.id.studentTV);
        teacher = findViewById(R.id.teacherTV);

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_EVENT_JSON);
        try {
            event.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {

        title.setText(event.getTitle());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(event.getBody().replace("\n", "<br>")));
        location.setText(event.getLocation());
        student.setText(event.getStudentName());
        teacher.setText(event.getTeacherName());
        university.setText(event.getUniversity());
        time.setText(event.getTime());
        if (event.getDate().equals(event.getEndDate())) {
            date.setText(event.getDate());
        } else {
            date.setText(event.getDate() + " تا " + event.getEndDate());
        }
        if (event.hasImage()) {
            Picasso.get().load(event.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(location, Color.parseColor("#222222"), Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(date, Color.BLACK, Constants.FONT_LIGHT);
        ResourceManager.getInstance().decorateTextView(time, Color.BLACK, Constants.FONT_LIGHT);
        ResourceManager.getInstance().decorateTextView(student, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(teacher, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(university, Color.BLACK, Constants.FONT_BOLD);
    }
}
