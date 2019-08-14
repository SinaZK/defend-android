package com.defend.android.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.activites.NewsActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.BookOrder;
import com.defend.android.data.BookShopItem;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

public class BookCartAdapter extends RecyclerView.Adapter<BookCartAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public CardView cardView;
        public Spinner spinner;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            spinner = view.findViewById(R.id.spinner);
            image = view.findViewById(R.id.image);
            cardView = view.findViewById(R.id.parent);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.news_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        BookShopItem shopItem = BookOrder.getInstance().getItems().get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        viewHolder.title.setText(shopItem.getBook().getTitle());

        if(shopItem.getBook().hasImage()) {
            Picasso.get().load(shopItem.getBook().getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.ic_launcher_no_image);
        }
    }

    @Override
    public int getItemCount() {
        return BookOrder.getInstance().getItems().size();
    }
}
