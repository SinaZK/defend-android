package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.EBookSearchListAdapter;
import com.defend.android.adapters.WarfareSearchListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.SearchToolbar;
import com.defend.android.data.EBook;
import com.defend.android.data.Warfare;
import com.defend.android.listeners.SearchListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WarfareSearchActivity extends Activity {

    SearchToolbar searchToolbar;
    ProgressBar progressBar;
    RelativeLayout noResultParent;
    LinearLayout resultParent;
    TextView noResultTextView;

    //TextView resultTextView;
    RecyclerView recyclerView;

    ArrayList<Warfare> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warfare_search);

        searchToolbar = findViewById(R.id.search_toolbar);
        progressBar = findViewById(R.id.progress);
        noResultParent = findViewById(R.id.no_result_parent);
        resultParent = findViewById(R.id.result_parent);
        noResultTextView = findViewById(R.id.no_result_tv);

        recyclerView = findViewById(R.id.recycler_view);

        searchToolbar.hideDrawerButton();

        initUI();
    }

    private void initUI() {
        ResourceManager.getInstance().decorateTextView(noResultTextView, Color.BLACK, Constants.FONT_BOLD);
        //ResourceManager.getInstance().decorateTextView(resultTextView, Color.parseColor("#222222"), Constants.FONT_BOLD);

        searchToolbar.setSearchListener(new SearchListener() {
            @Override
            public void onSearch(String search) {
                sendSearchRequest(search);
            }
        });
        searchToolbar.setFocus();
    }

    private void sendSearchRequest(String searchTerm) {
        if (searchTerm.length() == 0) {
            return;
        }

        data.clear();


        String url = Constants.API_URL + String.format(Constants.API_SEARCH_WARFARE, searchTerm);

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("_Search", error.toString());
                setProgress(false);
                showNoResult();
            }
        });
        setProgress(true);

        NetworkManager.getInstance().sendRequest(request);
    }

    private void onSuccess(JSONObject response) {
        showResult();

        boolean hasResult = false;
        if (updateData(response.optJSONArray("results"))) {
            hasResult = true;
        }

        if (!hasResult) {
            showNoResult();
        }
    }

    private boolean updateData(JSONArray response) {
        for(int i = 0;i < response.length();i++) {
            Warfare _data = new Warfare();
            _data.updateFromJson(response.optJSONObject(i));
            data.add(_data);
        }

        if (data.size() == 0) {
            recyclerView.setVisibility(View.GONE);
//            resultTextView.setVisibility(View.GONE);

            return false;
        } else {
            recyclerView.setVisibility(View.VISIBLE);
//            resultTextView.setVisibility(View.VISIBLE);
            WarfareSearchListAdapter adapter = new WarfareSearchListAdapter();
            adapter.setWarfares(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            return true;
        }
    }

    private void setProgress(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            resultParent.setVisibility(View.GONE);
            noResultParent.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showResult() {
        resultParent.setVisibility(View.VISIBLE);
        noResultParent.setVisibility(View.GONE);
    }

    private void showNoResult() {
        resultParent.setVisibility(View.GONE);
        noResultParent.setVisibility(View.VISIBLE);
    }
}
