package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdeaFragment extends Fragment {

    RelativeLayout myIdeasParent, newIdeaParent;
    TextView myIdeasTextView, newIdeaTextView;

    public IdeaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_idea, container, false);


        myIdeasParent = view.findViewById(R.id.my_ideas);
        newIdeaParent = view.findViewById(R.id.new_idea);
        myIdeasTextView = view.findViewById(R.id.my_label);
        newIdeaTextView = view.findViewById(R.id.new_label);

        initUI();

        return view;
    }

    private void initUI() {
        ResourceManager.getInstance().decorateTextView(myIdeasTextView, Color.WHITE, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(newIdeaTextView, Color.WHITE, Constants.FONT_BOLD);

        myIdeasParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        newIdeaParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
