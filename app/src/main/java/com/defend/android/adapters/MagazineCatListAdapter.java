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
import com.defend.android.activites.MagazineListActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.MagazineCategory;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MagazineCatListAdapter extends RecyclerView.Adapter<MagazineCatListAdapter.MyViewHolder> {

    private ArrayList<MagazineCategory> magazineCategories;

    private RecyclerLoadMoreListener loadMoreListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price;
        public ImageView image;
        public Button cartButton;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            price = view.findViewById(R.id.price);
            image = view.findViewById(R.id.image);
            cartButton = view.findViewById(R.id.cart_btn);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setMagazineCategories(ArrayList<MagazineCategory> magazineCategories) {
        this.magazineCategories = magazineCategories;
    }

    public void setLoadMoreListener(RecyclerLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.book_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final MagazineCategory magazineCategory = magazineCategories.get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(viewHolder.price, Color.RED);
        ResourceManager.getInstance().decorateButton(viewHolder.cartButton, Color.WHITE);
        viewHolder.cartButton.setBackgroundResource(R.drawable.btn_bg);
        viewHolder.title.setText(magazineCategory.getTitle());

        if(magazineCategory.hasImage()) {
            Picasso.get().load(magazineCategory.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.ic_launcher_no_image);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getInstance(), MagazineListActivity.class);
                intent.putExtra(Constants.EXTRA_MAGAZINE_JSON, magazineCategory.toJson().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApp.getInstance().startActivity(intent);
            }
        });

        viewHolder.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (i >= magazineCategories.size() - 5) {
            if(loadMoreListener != null) {
                loadMoreListener.loadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return magazineCategories.size();
    }
}
