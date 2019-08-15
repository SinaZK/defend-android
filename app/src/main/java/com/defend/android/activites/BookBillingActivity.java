package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.defend.android.MyApp;
import com.defend.android.Network.AuthObjectRequest;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.BookOrder;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;

import org.json.JSONObject;

public class BookBillingActivity extends Activity {

    EditText name, phone, address;
    RelativeLayout checkout;
    TextView buy;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_billing);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        checkout = findViewById(R.id.buy_parent);
        buy = findViewById(R.id.checkout_tv);
        progressBar = findViewById(R.id.progress);

        ResourceManager.getInstance().decorateEditText(name, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateEditText(phone, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateEditText(address, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(buy, Color.WHITE, Constants.FONT_BOLD);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

        setProgress(false);
    }

    private void checkout() {
        if(progressBar.getVisibility() == View.VISIBLE) return;
        if(!checkFields()) return;

        BookOrder.getInstance().addBillingInfo(name.getText().toString(), phone.getText().toString(), address.getText().toString());

        String url = Constants.API_URL + Constants.API_CHECKOUT_ORDER;

        JsonObjectRequest request = new AuthObjectRequest(Request.Method.POST, url,
                BookOrder.getInstance().toJson(false), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setProgress(false);
                try {
                    Utils.openUrl(response.optString("pay_url"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private boolean checkFields() {
        boolean isOk = true;
        if (name.getText().toString().length() == 0) {
            name.setError(getString(R.string.bill_required));
            isOk = false;
        }

        if (phone.getText().toString().length() == 0) {
            phone.setError(getString(R.string.bill_required));
            isOk = false;
        }

        if (address.getText().toString().length() == 0) {
            address.setError(getString(R.string.bill_required));
            isOk = false;
        }

        return isOk;
    }

    private void setProgress(boolean progress) {
        if(progress) {
            progressBar.setVisibility(View.VISIBLE);
            buy.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            buy.setVisibility(View.VISIBLE);
        }
    }
}
