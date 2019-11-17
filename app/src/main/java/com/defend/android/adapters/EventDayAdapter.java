package com.defend.android.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.activites.EventDetailActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.Event;
import com.defend.android.fragments.MonthViewFragment;
import com.defend.android.utils.ResourceManager;

import java.util.Locale;

public class EventDayAdapter extends RecyclerView.Adapter<EventDayAdapter.MyViewHolder> {

    private MonthViewFragment fragment;
    private int year;
    private int month;
    private int day;

    public EventDayAdapter(MonthViewFragment fragment, int year, int month, int day) {
        this.fragment = fragment;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView time;
        public TextView location;
        public CardView parent;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            location = view.findViewById(R.id.location);
            parent = view.findViewById(R.id.parent);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.event_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final Event event = fragment.findEventsByDate(day).get(i);

        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.WHITE, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(viewHolder.time, Color.WHITE, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(viewHolder.location, Color.parseColor("#c7c7ff"), Constants.FONT_REGULAR);
        viewHolder.title.setText(String.format(Locale.ENGLISH, "%s", event.getTitle()));
        viewHolder.location.setText(event.getLocation());
        viewHolder.time.setText(event.getTime());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getInstance(), EventDetailActivity.class);
                intent.putExtra(Constants.EXTRA_EVENT_JSON, event.toJson().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApp.getInstance().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment.findEventsByDate(day).size();
    }
}
