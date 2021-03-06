package com.defend.android.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.defend.android.Network.AuthArrayRequest;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.IdeaListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.data.Idea;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyIdeasActivity extends Activity {

    ActivityToolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RelativeLayout infoParent;
    TextView infoTextView;
    TextView topInfoTextView;
    ImageView topInfoImageView;

    private ArrayList<Idea> ideas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ideas);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress);
        infoParent = findViewById(R.id.bottom_view);
        infoTextView = findViewById(R.id.info_text);
        topInfoTextView = findViewById(R.id.info);
        topInfoImageView = findViewById(R.id.info_img);

        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.my_idea_activity_title));
        sendListRequest();

        ResourceManager.getInstance().decorateTextView(infoTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(topInfoTextView, Color.BLACK, Constants.FONT_BOLD);
        infoParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIdeasActivity.this, IdeaInfoActivity.class);
                MyIdeasActivity.this.startActivity(intent);
            }
        });

        topInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyIdeasActivity.this)
                        .setTitle("")
                        .setMessage(R.string.my_idea_info_dialog_text)
                        .setPositiveButton(R.string.my_idea_info_dialog_yes, null)
                        .setNegativeButton("", null)
                        .show();
            }
        });
        topInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyIdeasActivity.this)
                        .setTitle("")
                        .setMessage(R.string.my_idea_info_dialog_text)
                        .setPositiveButton(R.string.my_idea_info_dialog_yes, null)
                        .setNegativeButton("", null)
                        .show();
            }
        });
    }

    private void sendListRequest() {
        setProgress(true);
        String url = Constants.API_URL + Constants.API_IDEA_LIST;

        AuthArrayRequest request = new AuthArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setProgress(false);
                onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
            }
        });

        NetworkManager.getInstance().sendRequest(request);
    }

    private void onSuccess(JSONArray response) {
        ideas.clear();
        for (int i = 0;i < response.length();i++) {
            JSONObject ideaObj = response.optJSONObject(i);
            Idea idea = new Idea();
            idea.updateFromJson(ideaObj);
            ideas.add(idea);
        }

        initRV();
    }

    IdeaListAdapter adapter;
    private void initRV() {
        adapter = new IdeaListAdapter();
        adapter.setIdeas(ideas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setProgress(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
