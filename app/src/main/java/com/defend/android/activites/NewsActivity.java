package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

public class NewsActivity extends Activity {

    TextView title, body, date;
    ImageView image;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        date = findViewById(R.id.date);
        image = findViewById(R.id.image);

        int id = getIntent().getIntExtra(Constants.EXTRA_NEWS_ID, -1);
        if(id == -1) finish();
        news = NewsManager.getInstance().getNews().get(id);

        initUI();
    }

    private void initUI() {
        title.setText(news.getTitle());
        body.setText(news.getBody());
        date.setText(news.getDateTimeString());
        Picasso.get().load(news.getImageUrl())
                .error(R.drawable.ic_launcher_no_image)
                .into(image);

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(date, Color.BLACK, Constants.FONT_LIGHT);
    }
}
