package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.BookOrder;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class EBookDetailActivity extends Activity {

    RelativeLayout cartParent;
    TextView title, body, author, price, addToCartTV;
    ImageView image;
    Book book = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        author = findViewById(R.id.author);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        cartParent = findViewById(R.id.add_to_cart_parent);
        addToCartTV = findViewById(R.id.cart_tv);

        String jsonString = getIntent().getStringExtra(Constants.EXTRA_BOOK_JSON);
        try {
            book.updateFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        title.setText(book.getTitle());
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(book.getBody().replace("\n", "<br>")));
        author.setText(String.format(MyApp.getInstance().getString(R.string.book_author_str), book.getAuthor()));
        price.setText(String.format(MyApp.getInstance().getString(R.string.book_detail_item_price), book.getPrice()));
        if (book.hasImage()) {
            Picasso.get().load(book.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(image);
        }

        ResourceManager.getInstance().decorateTextView(title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(body, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(author, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(price, Color.RED, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(addToCartTV, Color.WHITE, Constants.FONT_REGULAR);

        cartParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookOrder.getInstance().addItem(book, 1);
            }
        });
    }
}
