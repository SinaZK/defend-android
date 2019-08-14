package com.defend.android.activites;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

public class BookCartActivity extends AppCompatActivity {

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

        initRV();
    }

    private void initRV() {
        
    }

    private void checkout() {

    }
}
