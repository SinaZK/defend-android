package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.NewsListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.NewsManager;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private int page = 1;
    private boolean isLoading;

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    TextView errorTextView;
    private NewsListAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        progressBar = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh);
        errorTextView = view.findViewById(R.id.load_error);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApp.getInstance());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsListAdapter();
        recyclerView.setAdapter(adapter);

        sendListRequest(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                sendListRequest(true);
            }
        });

        ResourceManager.getInstance().decorateTextView(errorTextView, Color.BLACK);

        return view;
    }

    private void sendListRequest(final boolean clearNews) {
        setLoading(true);

        String url = Constants.API_URL + Constants.API_LIST_NEWS + "?page=" + page;
        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setLoading(false);
                if(clearNews) NewsManager.getInstance().clearNews();
                refreshLayout.setRefreshing(false);
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
                refreshLayout.setRefreshing(false);
                errorTextView.setVisibility(View.VISIBLE);
            }
        });
        NetworkManager.getInstance().sendRequest(request);
    }

    private void onSuccess(JSONArray result) {
        NewsManager.getInstance().addNews(result);
        page ++;
        adapter.notifyDataSetChanged();
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if(isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            errorTextView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
