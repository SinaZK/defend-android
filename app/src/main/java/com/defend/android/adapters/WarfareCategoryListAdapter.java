package com.defend.android.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Warfare;
import com.defend.android.data.WarfareCategory;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WarfareCategoryListAdapter extends RecyclerView.Adapter<WarfareCategoryListAdapter.MyViewHolder> {

    private ArrayList<WarfareCategory> warfareCategories = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setWarfareCategories(ArrayList<WarfareCategory> warfares) {
        this.warfareCategories = warfares;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.warfare_category_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        WarfareCategory warfareCategory = warfareCategories.get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        viewHolder.title.setText(warfareCategory.getName());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MyApp.getInstance(), NewsActivity.class);
                //intent.putExtra(Constants.EXTRA_NEWS_ID, i);
                //MyApp.getInstance().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return warfareCategories.size();
    }
}
