package com.defend.android.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.DataStore;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private String TAG = "_Home";
    ProgressBar progressBar;
    RelativeLayout parent;
    RelativeLayout newsCard, bookCard, calendarCard;
    ImageView newsImageView;
    TextView newsTextView, bookTextView, calendarTextView;

    MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progress);
        parent = view.findViewById(R.id.parent);
        newsImageView = view.findViewById(R.id.news_image);
        newsTextView = view.findViewById(R.id.news_title);
        bookTextView = view.findViewById(R.id.book_title);
        calendarTextView = view.findViewById(R.id.calendar_title);
        newsCard = view.findViewById(R.id.news);
        bookCard = view.findViewById(R.id.book);
        calendarCard = view.findViewById(R.id.calendar);

        sendSyncRequest();

        initUI();

        return view;
    }

    private void initUI() {
        ResourceManager.getInstance().decorateTextView(newsTextView, Color.WHITE);
        ResourceManager.getInstance().decorateTextView(bookTextView, Color.WHITE);
        ResourceManager.getInstance().decorateTextView(calendarTextView, Color.WHITE);

        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment(Constants.MENU_NEWS);
            }
        });

        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment(Constants.MENU_BOOKS);
            }
        });

        calendarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment(Constants.MENU_EVENTS);
            }
        });
    }

    private boolean isLoading;
    private void sendSyncRequest() {
        if(isLoading) return;
        parent.setVisibility(View.GONE);

        String url = Constants.API_URL + Constants.API_SYNC;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);

                Log.i(TAG, "response = " + response);

                setNewsURL(response.optJSONObject("news").optString("image_url"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
                Log.i(TAG, "error = " + error);
            }
        });

        setProgress(true);
        NetworkManager.getInstance().sendRequest(request);
    }

    private void setProgress(boolean progress) {
        isLoading = progress;
        if(progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            showCards();
        }
    }

    private void setNewsURL(String url) {
        if (url.length() == 0) return;

        Log.i(TAG, "url = " + url);

        Picasso.get().load(url)
                .error(R.drawable.news_card)
                .into(newsImageView);
    }

    private void showCards() {
        parent.setVisibility(View.VISIBLE);


    }
}
