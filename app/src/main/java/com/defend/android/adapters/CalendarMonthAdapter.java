package com.defend.android.adapters;

import android.animation.AnimatorInflater;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.listeners.CalendarOnDaySelectListener;
import com.defend.android.utils.ResourceManager;

public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthAdapter.MyViewHolder> {

    private int TYPE_DAY = 1;
    private int TYPE_TOP = 2;

    private int month;
    private int year;
    private int startWeekDay;

    private int selectedItem = -1;
    private CalendarOnDaySelectListener listener;

    public CalendarMonthAdapter() {
        setDate(1398, 1);
    }

    public void setListener(CalendarOnDaySelectListener listener) {
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView selectedImage, todayImage;
        public RelativeLayout cardView;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            selectedImage = view.findViewById(R.id.selected);
            todayImage = view.findViewById(R.id.today);
            cardView = view.findViewById(R.id.parent);
            this.view = view.findViewById(R.id.view);
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

        if(selectedItem == i) {
            setSelectItem(viewHolder, i);
        }

        if(getDayOfMonth(i) != -1) {
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 7 * 7;
    }

    private void setViewTop(MyViewHolder holder, int position) {
        ResourceManager.getInstance().decorateTextView(holder.text, Color.parseColor("#c7c7c7ff"));
        holder.text.setText(CalendarUtils.getWeekDayChar(position));
    }

    private void setViewDay(MyViewHolder holder, int position) {
        ResourceManager.getInstance().decorateTextView(holder.text, Color.WHITE);
        holder.text.setText(getDayOfMonthStr(position));
        if(getDayOfMonthStr(position).length() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.cardView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(MyApp.getInstance(), R.anim.lift));
            }
        }
    }

    private void setSelectItem(MyViewHolder holder, int position) {
        holder.selectedImage.setVisibility(View.VISIBLE);
        holder.view.setVisibility(View.VISIBLE);
        holder.text.setTextColor(Color.BLACK);
    }

    private void invisibleView(MyViewHolder holder) {
        holder.todayImage.setVisibility(View.GONE);
        holder.selectedImage.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
    }

    private int getType(int position) {
        if(position < 7) return TYPE_TOP;

        return TYPE_DAY;
    }

    public void setDate(int year, int month) {
        this.month = month;
        this.year = year;

        startWeekDay = CalendarUtils.getFirstWeekDayOfMonth(year, month);
    }

    private int getDayOfMonth(int position) {
        if (position < 7) return -1;
        int startDay = 6 + startWeekDay;

        if (position <= startDay) return -1;

        int d = position - (startDay);
        if (d > CalendarUtils.getDaysInMonth(year, month)) return -1;

        return d;
    }

    private String getDayOfMonthStr(int position) {
        int day = getDayOfMonth(position);
        if(day == -1) return "";

        return String.valueOf(day);
    }

    private void selectItem(int position) {
        if(selectedItem != -1) {
            notifyItemChanged(selectedItem);
        }
        selectedItem = position;
        notifyItemChanged(selectedItem);

        if(listener != null) {
            listener.onDaySelect(year, month, getDayOfMonth(position));
        }
    }
}
