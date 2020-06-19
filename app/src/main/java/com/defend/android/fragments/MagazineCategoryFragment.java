package com.defend.android.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.activites.BookCartActivity;
import com.defend.android.activites.BookSearchActivity;
import com.defend.android.adapters.BookListAdapter;
import com.defend.android.adapters.MagazineCatListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.BookOrder;
import com.defend.android.data.MagazineCategory;
import com.defend.android.listeners.BookAddToCartListener;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MagazineCategoryFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;

    private int page = 1;

    private ArrayList <MagazineCategory> magazineCategories = new ArrayList<>();

    public MagazineCategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_magazine_cat, container, false);

        recyclerView = view.findViewById(R.id.book_rv);
        refreshLayout = view.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                senMagRequest(true);
            }
        });

        senMagRequest(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isLoading;
    private void senMagRequest(final boolean clear) {
        if(isLoading) return;

        String url = Constants.API_URL + Constants.API_LIST_MAGAZINE_CATEGORIES + "?page=" + page;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                if(clear) {
                    magazineCategories.clear();
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
            MagazineCategory magazineCategory = new MagazineCategory();
            magazineCategory.updateFromJson(array.optJSONObject(i));
            Log.i("_Magazine", array.optJSONObject(i).toString());

            magazineCategories.add(magazineCategory);
        }

        if(clear) {
            initRV();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    MagazineCatListAdapter adapter;
    private void initRV() {
        adapter = new MagazineCatListAdapter();
        adapter.setMagazineCategories(magazineCategories);
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
