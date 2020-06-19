package com.defend.android.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.MagazineListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.data.Magazine;
import com.defend.android.data.MagazineCategory;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MagazineListActivity extends Activity {

    ActivityToolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    TextView topFilterTextView;
    ImageView topFilterImageView;

    private int page = 1;
    private MagazineCategory magazineCategory = new MagazineCategory();

    private ArrayList<Magazine> magazines = new ArrayList<>();
    private int[] years = {
            1390, 1391, 1392, 1393, 1394, 1395
    };
    private int year = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_list);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.book_rv);
        refreshLayout = findViewById(R.id.refresh);
        topFilterTextView = findViewById(R.id.filter);
        topFilterImageView = findViewById(R.id.filter_img);

        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.magazine_title));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                senMagRequest(true);
            }
        });

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_MAGAZINE_JSON);
        try {
            magazineCategory.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResourceManager.getInstance().decorateTextView(topFilterTextView, Color.WHITE, Constants.FONT_BOLD);
        topFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MagazineListActivity.this)
                        .setTitle("")
                        .setMessage(R.string.my_idea_info_dialog_text)
                        .setPositiveButton(R.string.my_idea_info_dialog_yes, null)
                        .setNegativeButton("", null)
                        .show();
            }
        });
        topFilterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MagazineListActivity.this)
                        .setTitle("")
                        .setMessage(R.string.my_idea_info_dialog_text)
                        .setPositiveButton(R.string.my_idea_info_dialog_yes, null)
                        .setNegativeButton("", null)
                        .show();
            }
        });

        senMagRequest(true);
    }

    private boolean isLoading;
    private void senMagRequest(final boolean clear) {
        if(isLoading) return;

        String url = Constants.API_URL + Constants.API_LIST_MAGAZINE_ALL + "?category=" + magazineCategory.getId() + "&page=" + page;
        if (year != -1) {
            url += "&year=" + year;
        }

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                if(clear) {
                    magazines.clear();
                }

                onSuccess(response.optJSONArray("results"), clear);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
            }
        });

        setProgress(true);
        NetworkManager.getInstance().sendRequest(request);
    }

    private void setProgress(boolean progress) {
        refreshLayout.setRefreshing(progress);
        isLoading = progress;
    }

    private void onSuccess(JSONArray array, boolean clear) {
        for(int i = 0;i < array.length();i++) {
            Magazine magazine = new Magazine();
            magazine.updateFromJson(array.optJSONObject(i));
            Log.i("_Magazine", array.optJSONObject(i).toString());

            magazines.add(magazine);
        }

        if(clear) {
            initRV();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    MagazineListAdapter adapter;
    private void initRV() {
        adapter = new MagazineListAdapter();
        adapter.setMagazines(magazines);
        adapter.setLoadMoreListener(new RecyclerLoadMoreListener() {
            @Override
            public void loadMore() {
                if(!isLoading) {
                    page += 1;
                    senMagRequest(false);
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(MyApp.getInstance(), 2));
        recyclerView.setAdapter(adapter);
    }
}
