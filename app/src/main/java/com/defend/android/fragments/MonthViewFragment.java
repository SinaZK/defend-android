package com.defend.android.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.adapters.EventDayAdapter;
import com.defend.android.calendar.CalendarUtils;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.CalendarMonthView;
import com.defend.android.data.Event;
import com.defend.android.listeners.CalendarOnDaySelectListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthViewFragment extends Fragment {

    CalendarMonthView calendarView;
    RecyclerView eventRecyclerView;
    ProgressBar progressBar;
    TextView monthTextView;
    private int month, year;

    private ArrayList<Event> events = new ArrayList<>();

    public MonthViewFragment() {
        // Required empty public constructor
    }

    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_view, container, false);

        progressBar = view.findViewById(R.id.progress);
        eventRecyclerView = view.findViewById(R.id.event_rv);
        monthTextView = view.findViewById(R.id.month_text);
        ResourceManager.getInstance().decorateTextView(monthTextView, Color.WHITE, Constants.FONT_BOLD);
        monthTextView.setText(String.format(Locale.ENGLISH, "%s %d", CalendarUtils.getMonthPersianString(month), year
        ));

        calendarView = view.findViewById(R.id.calendar);
        calendarView.setListener(new CalendarOnDaySelectListener() {
            @Override
            public void onDaySelect(int year, int month, int day) {
                setSelectedDay(day);
            }
        });
        calendarView.setDate(year, month); //Create view

        initRV();
        sendRequest();

        return view;
    }

    public void sendRequest() {
        String url = Constants.API_URL + Constants.API_LIST_MONTH_EVENTS;

        JSONObject object = new JSONObject();
        try {
            object.put("year", year);
            object.put("month", month);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AuthObjectRequest request = new AuthObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addEvents(response.optJSONArray("results"));
                calendarView.setEvents(events);
                setProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgress(false);
            }
        });

        setProgress(true);
        NetworkManager.getInstance().sendRequest(request);
    }

    public void addEvents(JSONArray array) {
        events.clear();
        for (int i = 0;i < array.length();i++) {
            Event event = new Event();
            event.updateFromJson(array.optJSONObject(i));
            events.add(event);
        }
    }

    public ArrayList<Event> findEventsByDate(int d) {
        ArrayList<Event> dayEvents = new ArrayList<>();
        for(int i = 0;i < events.size();i++) {
            if(events.get(i).getDay() == d && events.get(i).getMonth() == month && events.get(i).getYear() == year) {
                dayEvents.add(events.get(i));
            }
        }

        return dayEvents;
    }

    private void setProgress(boolean p) {
        if(p) {
            eventRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    EventDayAdapter adapter;
    private void initRV() {
        adapter = new EventDayAdapter(this, year, month, 0);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setAdapter(adapter);
    }

    public void setSelectedDay(int day) {
        adapter.setDay(day);
        synchronized(adapter) {
            adapter.notifyDataSetChanged();
        }
    }

}
