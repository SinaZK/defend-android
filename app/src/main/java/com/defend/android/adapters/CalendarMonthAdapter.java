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
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.utils.ResourceManager;

public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthAdapter.MyViewHolder> {

    private int TYPE_DAY = 1;
    private int TYPE_TOP = 2;

    private int month = 1;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView selectedImage, todayImage;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            selectedImage = view.findViewById(R.id.selected);
            todayImage = view.findViewById(R.id.today);
            cardView = view.findViewById(R.id.parent);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.calendar_month_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        invisibleView(viewHolder);
        if(getType(i) == TYPE_TOP) {
            setViewTop(viewHolder, i);
        } else {
            setViewDay(viewHolder, i);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 6 * 7;
    }

    private void setViewTop(MyViewHolder holder, int position) {
        ResourceManager.getInstance().decorateTextView(holder.text, Color.parseColor("#c7c7c7ff"));
        holder.text.setText(CalendarUtils.getWeekDayChar(position));
    }

    private void setViewDay(MyViewHolder holder, int position) {
        ResourceManager.getInstance().decorateTextView(holder.text, Color.WHITE);
        holder.text.setText(String.valueOf(position));
    }

    private void invisibleView(MyViewHolder holder) {
        holder.todayImage.setVisibility(View.GONE);
        holder.selectedImage.setVisibility(View.GONE);
    }

    private int getType(int position) {
        if(position < 7) return TYPE_TOP;

        return TYPE_DAY;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}