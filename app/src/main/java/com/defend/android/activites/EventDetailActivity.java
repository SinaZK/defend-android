package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Event;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

public class EventDetailActivity extends Activity {

    TextView title, body, date;
    ImageView image;
    Event event = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        date = findViewById(R.id.date);
        image = findViewById(R.id.image);

        //int id = getIntent().getIntExtra(Constants.EXTRA_event_ID, -1);
        //if (id == -1) finish();
        //event = eventManager.getInstance().getevent().get(id);

        initUI();
    }

    private void initUI() {
        title.setText(event.getTitle());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(event.getBody().replace("\n", "<br>")));
        date.setText(event.getDate());
        if (event.hasImage()) {
            Picasso.get().load(event.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(date, Color.BLACK, Constants.FONT_LIGHT);
    }
}
