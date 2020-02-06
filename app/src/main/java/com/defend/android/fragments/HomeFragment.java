package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.defend.android.adapters.HomeItemAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.HomeItem;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private String TAG = "_Home";
    ProgressBar progressBar;
    RecyclerView recyclerView;

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
        recyclerView = view.findViewById(R.id.recycler_view);

        sendSyncRequest();

        adapter = new HomeItemAdapter();

        return view;
    }

    private boolean isLoading;
    private void sendSyncRequest() {
        if(isLoading) return;

        String url = Constants.API_URL + Constants.API_SYNC;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);

                setNewsURL(response.optJSONObject("news").optString("image_url"));
                initRV();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
                initRV();
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
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            showCards();
        }
    }

    private void setNewsURL(String url) {
        if (url.length() == 0) return;

        adapter.setNewsURL(url);
    }

    private void showCards() {
        recyclerView.setVisibility(View.VISIBLE);

    }

    HomeItemAdapter adapter;
    private void initRV() {
        ArrayList <HomeItem> items = new ArrayList<>();
        items.add(new HomeItem(getString(R.string.card_news), Constants.MENU_NEWS, R.drawable.ic_launcher_no_image));
        items.add(new HomeItem(getString(R.string.card_calendar), Constants.MENU_EVENTS, R.drawable.calendar_512));
        items.add(new HomeItem(getString(R.string.card_book), Constants.MENU_BOOKS, R.drawable.book));
        items.add(new HomeItem(getString(R.string.card_magazine), Constants.MENU_MAGAZINES, R.drawable.magazine_color));
        items.add(new HomeItem(getString(R.string.card_atlas), Constants.MENU_WARFARE, R.drawable.warfare_col));
        items.add(new HomeItem(getString(R.string.card_info), Constants.MENU_INFOGRAPHIC, R.drawable.info_img));
        items.add(new HomeItem(getString(R.string.card_tes), Constants.MENU_NEWEVENT, R.drawable.add_event_img4));

        adapter.setMainActivity(mainActivity);
        adapter.setItems(items);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i == 0) {
                    return 2;
                }

                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
