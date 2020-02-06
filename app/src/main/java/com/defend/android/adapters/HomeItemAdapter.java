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
import com.defend.android.activites.MainActivity;
import com.defend.android.data.BookOrder;
import com.defend.android.data.HomeItem;
import com.defend.android.listeners.BookCartItemChangeListener;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {

    private MainActivity mainActivity;
    private ArrayList <HomeItem> items;
    private String newsURL;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setItems(ArrayList<HomeItem> items) {
        this.items = items;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.home_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final HomeItem item = items.get(i);

        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.WHITE);

        viewHolder.image.setImageResource(item.getDrawable());
        viewHolder.title.setText(item.getTitle());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment(item.getMenuId());
            }
        });


        if (i == 0) { //News Card
            viewHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.image.setMinimumHeight(Utils.dpToPx(200));

            Picasso.get().load(newsURL)
                    .error(R.drawable.news_card)
                    .into(viewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}