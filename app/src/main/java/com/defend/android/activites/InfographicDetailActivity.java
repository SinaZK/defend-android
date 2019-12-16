package com.defend.android.activites;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Infographic;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class InfographicDetailActivity extends AppCompatActivity {

    TextView title, body;
    ImageView image;

    Infographic infographic = new Infographic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_infographic_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        image = findViewById(R.id.image);

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_WARFARE_JSON);
        try {
            infographic.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        title.setText(infographic.getName());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(infographic.getBody().replace("\n", "<br>")));

        if (infographic.hasImage()) {
            Picasso.get().load(infographic.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
    }
}
