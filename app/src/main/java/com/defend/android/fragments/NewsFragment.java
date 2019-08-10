package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private int page = 1;
    private boolean isLoading;

    private ProgressBar progressBar;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        progressBar = view.findViewById(R.id.progress);

        NewsManager.getInstance().clearNews();
        sendListRequest();

        return view;
    }

    private void sendListRequest() {
        setLoading(true);

        String url = Constants.API_URL + Constants.API_LIST_NEWS + "?page=" + page;
        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setLoading(false);
                try {
                    onSuccess(response.getJSONArray("results"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
            }
        });
    }

    private void onSuccess(JSONArray result) {
        NewsManager.getInstance().addNews(result);
        page ++;

    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
