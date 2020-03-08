package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.SearchToolbar;
import com.defend.android.listeners.SearchListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    MainActivity activity;
    SearchToolbar searchToolbar;
    ProgressBar progressBar;
    RelativeLayout noResultParent;
    LinearLayout resultParent;
    TextView noResultTextView;

    TextView atlasTextView, bookTextView, ebookTextView, magTextView, infoTextView;
    RecyclerView atlasRV, bookRV, ebookRV, magRV, infoRV;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchToolbar = view.findViewById(R.id.search_toolbar);
        progressBar = view.findViewById(R.id.progress);
        noResultParent = view.findViewById(R.id.no_result_parent);
        resultParent = view.findViewById(R.id.result_parent);
        noResultTextView = view.findViewById(R.id.no_result_tv);

        atlasTextView = view.findViewById(R.id.atlas_result);
        bookTextView = view.findViewById(R.id.book_result);
        ebookTextView = view.findViewById(R.id.ebook_result);
        magTextView = view.findViewById(R.id.magazine_result);
        infoTextView = view.findViewById(R.id.info_result);
        atlasRV = view.findViewById(R.id.atlas_rv);
        bookRV = view.findViewById(R.id.book_rv);
        ebookRV = view.findViewById(R.id.ebook_rv);
        magRV = view.findViewById(R.id.magazine_rv);
        infoRV = view.findViewById(R.id.info_rv);

        searchToolbar.setActivity(activity);

        initUI();

        return view;
    }

    private void initUI() {
        ResourceManager.getInstance().decorateTextView(noResultTextView, Color.BLACK, Constants.FONT_BOLD);

        ResourceManager.getInstance().decorateTextView(atlasTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(ebookTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(bookTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(infoTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(magTextView, Color.BLACK, Constants.FONT_BOLD);

        searchToolbar.setSearchListener(new SearchListener() {
            @Override
            public void onSearch(String search) {
                sendSearchRequest(search);
            }
        });
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    private void sendSearchRequest(String searchTerm) {
        if (searchTerm.length() == 0) {
            return;
        }

        String url = Constants.API_URL + Constants.API_SEARCH_ALL;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
                showNoResult();
            }
        });
        setProgress(true);

        NetworkManager.getInstance().sendRequest(request);
    }

    private void onSuccess(JSONObject response) {
        Log.i("_Search", response.toString());
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
