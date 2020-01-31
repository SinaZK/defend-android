package com.defend.android.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.activites.BookDetailActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.Idea;
import com.defend.android.listeners.BookAddToCartListener;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.MyViewHolder> {

    private ArrayList<Idea> ideas;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView stateTV, statePlaceTV, stateTextTV, stateTextPlaceTV, codeTV, codePlaceTV;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            stateTV = view.findViewById(R.id.state_tv);
            statePlaceTV = view.findViewById(R.id.state_place);
            stateTextTV = view.findViewById(R.id.state_text_tv);
            stateTextPlaceTV = view.findViewById(R.id.state_text_place);
            codeTV = view.findViewById(R.id.code_tv);
            codePlaceTV = view.findViewById(R.id.code_place);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setIdeas(ArrayList<Idea> ideas) {
        this.ideas = ideas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.idea_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        Idea idea = ideas.get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(viewHolder.stateTV, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(viewHolder.statePlaceTV, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(viewHolder.stateTextTV, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(viewHolder.stateTextPlaceTV, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(viewHolder.codeTV, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(viewHolder.codePlaceTV, Color.BLACK, Constants.FONT_REGULAR);

        viewHolder.title.setText(idea.getTitle());
        viewHolder.stateTV.setText(idea.getState());
        viewHolder.stateTextTV.setText(idea.getStateText());
        viewHolder.codeTV.setText(idea.getCode());
    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }
}
