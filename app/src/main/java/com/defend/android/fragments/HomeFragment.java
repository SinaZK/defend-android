package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.DataStore;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private String TAG = "_Home";
    ProgressBar progressBar;
    RelativeLayout parent;


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

        sendSyncRequest();

        return view;
    }

    private boolean isLoading;
    private void sendSyncRequest() {
        if(isLoading) return;

        String url = Constants.API_URL + Constants.API_SYNC;

        Log.i(TAG, "url = " + url);
        String token = DataStore.getInstance().getStringData(Constants.DATA_FIELD_TOKEN, "");
        Log.i(TAG, "token = " + token);

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

    }

    private void showCards() {
        parent.setVisibility(View.VISIBLE);


    }
}
