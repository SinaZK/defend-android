package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.BookOrder;
import com.defend.android.data.Magazine;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MagazineDetailActivity extends Activity {

    RelativeLayout cartParent;
    TextView title, body, price, addToCartTV, publish;
    ImageView image;
    Magazine magazine = new Magazine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        publish = findViewById(R.id.publish);
        cartParent = findViewById(R.id.add_to_cart_parent);
        addToCartTV = findViewById(R.id.cart_tv);

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_BOOK_JSON);
        try {
            magazine.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        title.setText(magazine.getTitle());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(magazine.getBody().replace("\n", "<br>")));
        publish.setText(String.format(MyApp.getInstance().getString(R.string.book_year_str), String.valueOf(magazine.getYear())));
        price.setText(String.format(MyApp.getInstance().getString(R.string.book_detail_item_price), magazine.getPrice()));
        if (magazine.hasImage()) {
            Picasso.get().load(magazine.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(publish, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(price, Color.RED, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(addToCartTV, Color.WHITE, Constants.FONT_REGULAR);
    }
}
