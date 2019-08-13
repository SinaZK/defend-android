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
import com.defend.android.data.Warfare;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class WarfareDetailActivity extends Activity {

    TextView title, body;
    ImageView image;

    Warfare warfare = new Warfare();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warfare_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        image = findViewById(R.id.image);

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_WARFARE_JSON);
        try {
            warfare.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        title.setText(warfare.getName());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(warfare.getBody().replace("\n", "<br>")));

        if (warfare.hasImage()) {
            Picasso.get().load(warfare.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
    }
}
