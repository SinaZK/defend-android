package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
    VideoView videoView;
    View videoBG;

    Warfare warfare = new Warfare();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warfare_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        image = findViewById(R.id.image);
        videoView = findViewById(R.id.video);
        videoBG = findViewById(R.id.video_bg);

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

        if (!warfare.hasVideo()) {
            videoBG.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);

        prepareVideo();
    }

    private void prepareVideo() {
        if (!warfare.hasVideo()) return;

        Uri video = Uri.parse(warfare.getVideoUrl());
        videoView.setVideoURI(video);
        videoView.setVisibility(View.VISIBLE);

        MediaController mediaController = new MediaController(this, false);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setZOrderOnTop(true); //Very important line, add it to Your code
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        videoView.start();
    }
}
