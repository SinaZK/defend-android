package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.BookOrder;
import com.defend.android.utils.ResourceManager;

public class BookCartActivity extends Activity {

    TextView totalTextView, valueTextView;
    TextView checkoutTextView;
    RecyclerView itemRecyclerView;
    RelativeLayout checkoutParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cart);

        checkoutTextView = findViewById(R.id.checkout_tv);
        totalTextView = findViewById(R.id.total_tv);
        valueTextView = findViewById(R.id.total_value_tv);
        itemRecyclerView = findViewById(R.id.item_rv);
        checkoutParent = findViewById(R.id.checkout_parent);

        ResourceManager.getInstance().decorateTextView(checkoutTextView, Color.WHITE, Constants.FONT_LIGHT);
        ResourceManager.getInstance().decorateTextView(totalTextView, Color.RED, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(valueTextView, Color.RED, Constants.FONT_BOLD);

        checkoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
            }
        });

        valueTextView.setText(String.format(MyApp.getInstance().getString(R.string.book_total_price_val),
                BookOrder.getInstance().getTotalPrice()));

        initRV();
    }

    private void initRV() {

    }

    private void checkout() {

    }
}
