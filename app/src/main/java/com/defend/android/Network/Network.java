package com.defend.android.Network;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Network {
    private static Network instance;
    private Activity activity;
    private RequestQueue queue;

    public static Network getInstance() {
        if(instance == null) instance = new Network();

        return instance;
    }

    public void init(Activity activity) {
        this.activity = activity;
        queue = Volley.newRequestQueue(activity);
    }

    public void sendRequest(Request request) {
        queue.add(request);
    }
}
