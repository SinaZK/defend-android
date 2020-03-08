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
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.activites.WarfareDetailActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.Warfare;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WarfareSearchListAdapter extends RecyclerView.Adapter<WarfareSearchListAdapter.MyViewHolder> {

    private ArrayList<Warfare> warfares = new ArrayList<>();

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

    public void setWarfares(ArrayList<Warfare> warfares) {
        this.warfares = warfares;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.warfare_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final Warfare warfare = warfares.get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        viewHolder.title.setText(warfare.getName());
        if(warfare.hasImage()) {
            Picasso.get().load(warfare.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.ic_launcher_no_image);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getInstance(), WarfareDetailActivity.class);
                intent.putExtra(Constants.EXTRA_WARFARE_JSON, warfare.toJson().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApp.getInstance().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return warfares.size();
    }
}
