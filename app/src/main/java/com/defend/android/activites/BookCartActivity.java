package com.defend.android.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.defend.android.adapters.BookCartAdapter;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.BookOrder;
import com.defend.android.data.BookShopItem;
import com.defend.android.listeners.BookCartItemChangeListener;
import com.defend.android.utils.ResourceManager;

import org.json.JSONObject;

public class BookCartActivity extends Activity {

    TextView totalTextView, valueTextView;
    TextView checkoutTextView;
    RecyclerView itemRecyclerView;
    RelativeLayout checkoutParent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cart);

        checkoutTextView = findViewById(R.id.checkout_tv);
        totalTextView = findViewById(R.id.total_tv);
        valueTextView = findViewById(R.id.total_value_tv);
        itemRecyclerView = findViewById(R.id.item_rv);
        checkoutParent = findViewById(R.id.checkout_parent);
        progressBar = findViewById(R.id.progress);

        ResourceManager.getInstance().decorateTextView(checkoutTextView, Color.WHITE, Constants.FONT_LIGHT);
        ResourceManager.getInstance().decorateTextView(totalTextView, Color.RED, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(valueTextView, Color.RED, Constants.FONT_BOLD);

        checkoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

        setTotalValueText();
        setProgress(false);

        initRV();
    }

    BookCartAdapter adapter;
    private void initRV() {
        adapter = new BookCartAdapter();
        adapter.setListener(new BookCartItemChangeListener() {
            @Override
            public void onChange(BookShopItem bookShopItem, int quantity) {
                BookOrder.getInstance().updateItem(bookShopItem.getBook(), quantity);
                setTotalValueText();
            }

            @Override
            public void onItemRemove() {
                setTotalValueText();
                if(BookOrder.getInstance().getItems().size() == 0) {
                    finish();
                }
            }
        });

        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getInstance()));
        itemRecyclerView.setAdapter(adapter);
    }

    private void checkout() {
        Intent intent = new Intent(MyApp.getInstance(), BookBillingActivity.class);
        MyApp.getInstance().startActivity(intent);
    }

    private void setProgress(boolean progress) {
        if(progress) {
            progressBar.setVisibility(View.VISIBLE);
            checkoutTextView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            checkoutTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setTotalValueText() {
        valueTextView.setText(String.format(MyApp.getInstance().getString(R.string.book_total_price_val),
                BookOrder.getInstance().getTotalPrice()));
    }
}
