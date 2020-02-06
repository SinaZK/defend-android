package com.defend.android.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.activites.BookCartActivity;
import com.defend.android.adapters.BookListAdapter;
import com.defend.android.adapters.EBookListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.BookOrder;
import com.defend.android.data.EBook;
import com.defend.android.listeners.BookAddToCartListener;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EBookShopFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;

    RelativeLayout cartParent;
    TextView cartTextView;

    private int page = 1;

    private ArrayList <EBook> eBooks = new ArrayList<>();

    public EBookShopFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ebook_shop, container, false);

        cartParent = view.findViewById(R.id.cart_parent);
        cartTextView = view.findViewById(R.id.cart_tv);
        recyclerView = view.findViewById(R.id.book_rv);
        refreshLayout = view.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                sendBookRequest(true);
            }
        });

        ResourceManager.getInstance().decorateTextView(cartTextView, Color.WHITE, Constants.FONT_BOLD);
        cartParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCartActivity();
            }
        });

        sendBookRequest(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private boolean isLoading;
    private void sendBookRequest(final boolean clear) {
        if(isLoading) return;

        String url = Constants.API_URL + Constants.API_LIST_EBOOKS + "?page=" + page;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                if(clear) {
                    eBooks.clear();
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
            EBook book = new EBook();
            book.updateFromJson(array.optJSONObject(i));

            eBooks.add(book);
        }

        if(clear) {
            initRV();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    EBookListAdapter adapter;
    private void initRV() {
        adapter = new EBookListAdapter();
        adapter.seteBooks(eBooks);
        adapter.setLoadMoreListener(new RecyclerLoadMoreListener() {
            @Override
            public void loadMore() {
                if(!isLoading) {
                    page += 1;
                    sendBookRequest(false);
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(MyApp.getInstance(), 2));
        recyclerView.setAdapter(adapter);
    }

    private void openCartActivity() {
        Intent intent = new Intent(MyApp.getInstance(), BookCartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApp.getInstance().startActivity(intent);
    }

}
