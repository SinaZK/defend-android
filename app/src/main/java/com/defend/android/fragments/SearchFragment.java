package com.defend.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defend.android.R;
import com.defend.android.activites.MainActivity;
import com.defend.android.customViews.SearchToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    MainActivity activity;
    SearchToolbar searchToolbar;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchToolbar = view.findViewById(R.id.search_toolbar);

        return view;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
        searchToolbar.setActivity(activity);
    }
}
