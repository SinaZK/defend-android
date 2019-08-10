package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

public class NewsActivity extends Activity {

    TextView title, body, date;
    ImageView image;
    VideoView videoView;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        date = findViewById(R.id.date);
        image = findViewById(R.id.image);
        videoView = findViewById(R.id.video);

        int id = getIntent().getIntExtra(Constants.EXTRA_NEWS_ID, -1);
        if (id == -1) finish();
        news = NewsManager.getInstance().getNews().get(id);

        initUI();
    }

    private void initUI() {
        title.setText(news.getTitle());
        body.setText(news.getBody());
        date.setText(news.getDateTimeString());
        if (news.hasImage()) {
            Picasso.get().load(news.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(date, Color.BLACK, Constants.FONT_LIGHT);

        prepareVideo();
    }

    private void prepareVideo() {
        if (!news.hasVideo()) return;

        Uri video = Uri.parse(news.getVideoUrl());
        videoView.setVideoURI(video);
        videoView.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);

        videoView.setZOrderOnTop(true); //Very important line, add it to Your code
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }
}
