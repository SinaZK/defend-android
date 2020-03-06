package com.defend.android.Network;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.MultiPartRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.defend.android.MyApp;

public class NetworkManager {
    private static NetworkManager instance;
    private RequestQueue queue;

    private NetworkManager() {
        queue = Volley.newRequestQueue(MyApp.getInstance());
    }

    public static NetworkManager getInstance() {
        if(instance == null) instance = new NetworkManager();

        return instance;
    }

    public void sendRequest(JsonObjectRequest request) {
        queue.add(request);
    }

    public void sendRequest(Request request) {
        queue.add(request);
    }

    public void sendRequest(SimpleMultiPartRequest request) {
        queue.add(request);
    }

    public void sendRequest(MultiPartRequest request) {
        queue.add(request);
    }

    public void sendRequest(JsonArrayRequest request) {
        queue.add(request);
    }

    public void sendRequest(JsonObjectRequest request, int timeout) {
        if (request == null) {
            return;
        }

        if (queue == null) {
            return;
        }

        request.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}
