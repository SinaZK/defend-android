package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Infographic;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class InfographicDetailActivity extends Activity {

    TextView title, topText, bottomText;
    ImageView image;

    Infographic infographic = new Infographic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_infographic_detail);

        title = findViewById(R.id.title);
        topText = findViewById(R.id.top_text);
        bottomText = findViewById(R.id.bottom_text);
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
        topText.setMovementMethod(LinkMovementMethod.getInstance());
        topText.setText(Html.fromHtml(infographic.getTopText().replace("\n", "<br>")));
        bottomText.setMovementMethod(LinkMovementMethod.getInstance());
        bottomText.setText(Html.fromHtml(infographic.getBottomText().replace("\n", "<br>")));

        if (infographic.getBottomText().length() == 0) {
            bottomText.setVisibility(View.GONE);
        }

        if (infographic.getTopText().length() == 0) {
            topText.setVisibility(View.GONE);
        }

        if (infographic.hasImage()) {
            Picasso.get().load(infographic.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(topText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(bottomText, Color.BLACK, Constants.FONT_REGULAR);
    }
}
