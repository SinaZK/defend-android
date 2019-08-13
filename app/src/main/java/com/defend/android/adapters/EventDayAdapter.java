package com.defend.android.adapters;

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
import com.defend.android.constants.Constants;
import com.defend.android.data.Event;
import com.defend.android.fragments.MonthViewFragment;
import com.defend.android.utils.ResourceManager;

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
        public CardView parent;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
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
        Event event = fragment.findEventsByDate(day).get(i);

        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        viewHolder.title.setText(event.getTitle());

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment.findEventsByDate(day).size();
    }
}
