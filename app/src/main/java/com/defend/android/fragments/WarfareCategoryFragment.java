package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.WarfareCategoryListAdapter;
import com.defend.android.adapters.WarfareListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Warfare;
import com.defend.android.data.WarfareCategory;
import com.defend.android.listeners.WarfareCategoryItemSelectListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarfareCategoryFragment extends Fragment {

    ProgressBar progressBar;
    RecyclerView catRV, warfareRV;
    Button backButton;
    private ArrayList<Integer> categoryQueue = new ArrayList<>();
    private ArrayList<Warfare> warfares = new ArrayList<>();
    private ArrayList<WarfareCategory> warfareCategories = new ArrayList<>();

    public WarfareCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warfare_category, container, false);

        catRV = view.findViewById(R.id.category_rv);
        warfareRV = view.findViewById(R.id.warfare_rv);
        progressBar = view.findViewById(R.id.progress);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        categoryQueue.add(0);
        sendAtlasRequest();

        return view;
    }

    private void sendAtlasRequest() {
        String url = Constants.API_URL +
                String.format(Locale.ENGLISH, Constants.API_LIST_ATLAS_CATEGORY, categoryQueue.get(categoryQueue.size() - 1));

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                updateWarfares(response.optJSONObject("results").optJSONArray("atlases"));
                updateWarfareCategories(response.optJSONObject("results").optJSONArray("categories"));
                updateRVs();
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

    private void updateWarfares(JSONArray array) {
        warfares.clear();

        for(int i = 0;i < array.length();i++) {
            warfares.add(new Warfare().updateFromJson(array.optJSONObject(i)));
        }
    }

    private void updateWarfareCategories(JSONArray array) {
        warfareCategories.clear();

        for(int i = 0;i < array.length();i++) {
            warfareCategories.add(new WarfareCategory().updateFromJson(array.optJSONObject(i)));
        }
    }

    WarfareListAdapter warfareListAdapter;
    WarfareCategoryListAdapter warfareCategoryListAdapter;
    private void updateRVs() {
        warfareListAdapter = new WarfareListAdapter();
        warfareListAdapter.setWarfares(warfares);
        warfareRV.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        warfareRV.setAdapter(warfareListAdapter);

        warfareCategoryListAdapter = new WarfareCategoryListAdapter();
        warfareCategoryListAdapter.setWarfareCategories(warfareCategories);
        warfareCategoryListAdapter.setListener(new WarfareCategoryItemSelectListener() {
            @Override
            public void onCategorySelect(int categoryId) {
                categoryQueue.add(categoryId);
                sendAtlasRequest();
            }
        });
        catRV.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        catRV.setAdapter(warfareCategoryListAdapter);
    }

    private void setProgress(boolean progress) {
        if(progress) {
            this.progressBar.setVisibility(View.VISIBLE);
            catRV.setVisibility(View.GONE);
            warfareRV.setVisibility(View.GONE);
        } else {
            this.progressBar.setVisibility(View.GONE);
            catRV.setVisibility(View.VISIBLE);
            warfareRV.setVisibility(View.VISIBLE);
        }

    }

    private void goBack() {
        if(categoryQueue.size() > 1) {
            categoryQueue.remove(categoryQueue.size() - 1);
            sendAtlasRequest();
        }
    }

}
