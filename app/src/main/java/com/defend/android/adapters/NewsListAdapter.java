package com.defend.android.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.data.News;
import com.defend.android.data.NewsManager;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, datetime;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            datetime = view.findViewById(R.id.datetime);
            image = view.findViewById(R.id.image);
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
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        News news = NewsManager.getInstance().getNews().get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(viewHolder.datetime, Color.parseColor("#777777"));
        viewHolder.title.setText(news.getTitle());
        viewHolder.datetime.setText(news.getDateTimeString());
        Picasso.get().load(news.getImageUrl())
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return NewsManager.getInstance().getNews().size();
    }
}
