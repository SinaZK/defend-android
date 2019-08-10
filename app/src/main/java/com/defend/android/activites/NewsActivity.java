package com.defend.android.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    TextView title, body;
    ImageView image;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        image = findViewById(R.id.image);

        int id = getIntent().getIntExtra(Constants.EXTRA_NEWS_ID, -1);
        if(id == -1) finish();
        news = NewsManager.getInstance().getNews().get(id);

        initUI();
    }

    private void initUI() {
        title.setText(news.getTitle());
        body.setText(news.getBody());
        Picasso.get().load(news.getImageUrl())
                .error(R.drawable.ic_launcher_no_image)
                .into(image);
    }
}
