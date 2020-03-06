package com.defend.android.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.defend.android.constants.Constants;
import com.defend.android.data.DataStore;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sinazk on 12/3/17.
 * 11:11
 */

public class AuthSimpleMultipartRequest extends SimpleMultiPartRequest {
    public AuthSimpleMultipartRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public AuthSimpleMultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", "JWT " + DataStore.getInstance().getStringData(Constants.DATA_FIELD_TOKEN, ""));

        return params;
    }

//    @Override
//    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        try {
//            String json = new String(
//                    response.data,
//                    "UTF-8"
//            );
//
//            if (json.length() == 0) {
//                return Response.success(
//                        null,
//                        HttpHeaderParser.parseCacheHeaders(response)
//                );
//            } else {
//                return super.parseNetworkResponse(response);
//            }
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        }
//    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }
}
