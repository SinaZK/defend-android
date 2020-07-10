package com.defend.android.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.activites.BookSearchActivity;
import com.defend.android.activites.InfoSearchActivity;
import com.defend.android.adapters.InfoListAdapter;
import com.defend.android.adapters.WarfareCategoryListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Infographic;
import com.defend.android.data.Warfare;
import com.defend.android.data.WarfareCategory;
import com.defend.android.listeners.WarfareCategoryItemSelectListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfographicCategoryFragment extends Fragment {

    private String TAG = "_Infographic";

    ProgressBar progressBar;
    RecyclerView catRV, infoRV;
    RelativeLayout topParent;
    ImageView backButton;
    TextView categoryNameTextView, searchTextView;

    RelativeLayout searchToolbar;

    private ArrayList<Integer> categoryQueue = new ArrayList<>();
    private ArrayList<String> categoryQueueName = new ArrayList<>();
    private ArrayList<Infographic> info = new ArrayList<>();
    private ArrayList<WarfareCategory> infoCategories = new ArrayList<>();

    public InfographicCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warfare_category, container, false);

        catRV = view.findViewById(R.id.category_rv);
        infoRV = view.findViewById(R.id.warfare_rv);
        progressBar = view.findViewById(R.id.progress);
        topParent = view.findViewById(R.id.top_parent);
        categoryNameTextView = view.findViewById(R.id.category_tv);
        searchToolbar = view.findViewById(R.id.search_toolbar);
        searchTextView = view.findViewById(R.id.search_edit_text);

        categoryQueue.add(0);
        categoryQueueName.add(getString(R.string.warfare_parent_cat_tv));
        onCategoryChange();
        sendAtlasRequest();

        initUI();

        return view;
    }

    private void initUI() {
        ResourceManager.getInstance().decorateTextView(categoryNameTextView, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(searchTextView, Color.BLACK, Constants.FONT_BOLD);

        searchToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getInstance(), InfoSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApp.getInstance().startActivity(intent);
            }
        });
    }

    public void setBackButton(ImageView backButton) {
        this.backButton = backButton;
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void sendAtlasRequest() {
        String url = Constants.API_URL +
                String.format(Locale.ENGLISH, Constants.API_LIST_INFOGRAPHIC_CATEGORY, categoryQueue.get(categoryQueue.size() - 1));

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                updateInfos(response.optJSONObject("results").optJSONArray("info"));
                updateInfoCategories(response.optJSONObject("results").optJSONArray("categories"));
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

    private void updateInfos(JSONArray array) {
        info.clear();

        for(int i = 0;i < array.length();i++) {
            info.add(new Infographic().updateFromJson(array.optJSONObject(i)));
        }
    }

    private void updateInfoCategories(JSONArray array) {
        infoCategories.clear();

        for(int i = 0;i < array.length();i++) {
            infoCategories.add(new WarfareCategory().updateFromJson(array.optJSONObject(i)));
        }
    }

    InfoListAdapter infoListAdapter;
    WarfareCategoryListAdapter infoCategoryListAdapter;
    private void updateRVs() {
        infoListAdapter = new InfoListAdapter();
        infoListAdapter.setInfographics(info);
        infoRV.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        infoRV.setAdapter(infoListAdapter);
//        if(infoCategories.size() == 0) {
//            infoRV.setMaxSize(1000);
//        }

        infoCategoryListAdapter = new WarfareCategoryListAdapter();
        infoCategoryListAdapter.setWarfareCategories(infoCategories);
        infoCategoryListAdapter.setListener(new WarfareCategoryItemSelectListener() {
            @Override
            public void onCategorySelect(int categoryId, String name) {
                categoryQueue.add(categoryId);
                categoryQueueName.add(name);
                sendAtlasRequest();
                onCategoryChange();
            }
        });
        catRV.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        catRV.setAdapter(infoCategoryListAdapter);
    }

    private void setProgress(boolean progress) {
        if(progress) {
            this.progressBar.setVisibility(View.VISIBLE);
            catRV.setVisibility(View.GONE);
            infoRV.setVisibility(View.GONE);
        } else {
            this.progressBar.setVisibility(View.GONE);
            catRV.setVisibility(View.VISIBLE);
            infoRV.setVisibility(View.VISIBLE);
        }

    }

    private void goBack() {
        if(categoryQueue.size() > 1) {
            categoryQueue.remove(categoryQueue.size() - 1);
            categoryQueueName.remove(categoryQueueName.size() - 1);
            sendAtlasRequest();
        }
        onCategoryChange();
    }

    public void onCategoryChange() {
        if (categoryQueue.size() <= 1) {
            topParent.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
        } else {
            topParent.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            categoryNameTextView.setText(categoryQueueName.get(categoryQueueName.size() - 1));
        }
    }

    public void onBackPressed() {
        goBack();
    }

    public int queueSize() {
        return categoryQueue.size();
    }

}
