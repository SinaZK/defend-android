package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.BookListAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookShopFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    private int page = 1;

    private ArrayList <Book> books = new ArrayList<>();

    public BookShopFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_book_shop, container, false);

        recyclerView = view.findViewById(R.id.book_rv);
        refreshLayout = view.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendBookRequest(true);
            }
        });

        sendBookRequest(true);

        return view;
    }

    private void sendBookRequest(final boolean clear) {
        String url = Constants.API_URL + Constants.API_LIST_BOOKS + "?page = " + page;

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                if(clear) {
                    books.clear();
                }

                onSuccess(response.optJSONArray("results"));
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
    }

    private void onSuccess(JSONArray array) {
        for(int i = 0;i < array.length();i++) {
            Book book = new Book();
            book.updateFromJson(array.optJSONObject(i));

            books.add(book);
        }

        initRV();
    }

    BookListAdapter adapter;
    private void initRV() {
        adapter = new BookListAdapter();
        adapter.setBooks(books);

        recyclerView.setLayoutManager(new GridLayoutManager(MyApp.getInstance(), 2));
        recyclerView.setAdapter(adapter);
    }

}
