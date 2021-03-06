package com.defend.android.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.Book;
import com.defend.android.data.BookOrder;
import com.defend.android.data.BookShopItem;
import com.defend.android.listeners.BookCartItemChangeListener;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookCartAdapter extends RecyclerView.Adapter<BookCartAdapter.MyViewHolder> {

    private BookCartItemChangeListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price;
        public ImageView image;
        public CardView cardView;
        public Spinner spinner;
        public ImageView removeImage;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            spinner = view.findViewById(R.id.spinner);
            image = view.findViewById(R.id.image);
            price = view.findViewById(R.id.price);
            removeImage = view.findViewById(R.id.remove_img);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setListener(BookCartItemChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.book_cart_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final BookShopItem shopItem = BookOrder.getInstance().getItems().get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(viewHolder.price, Color.RED, Constants.FONT_BOLD);
        viewHolder.title.setText(shopItem.getBook().getTitle());
        viewHolder.price.setText(String.format(MyApp.getInstance().getString(R.string.book_item_price),
                shopItem.getBook().getPrice()));

        if (shopItem.getBook().hasImage()) {
            Picasso.get().load(shopItem.getBook().getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.ic_launcher_no_image);
        }

        viewHolder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(i, shopItem.getBook());
                if(listener != null) {
                    listener.onItemRemove();
                }
            }
        });

        initSpinner(viewHolder, viewHolder.spinner, shopItem);
    }

    @Override
    public int getItemCount() {
        return BookOrder.getInstance().getItems().size();
    }

    private void initSpinner(final MyViewHolder viewHolder, Spinner spinner, final BookShopItem item) {
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyApp.getInstance(), R.layout.spinner_item, list) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ResourceManager.getInstance().decorateTextView((TextView) view, Color.BLACK);

                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                ResourceManager.getInstance().decorateTextView((TextView) view, Color.BLACK);

                return view;
            }
        };
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(item.getQuantity() - 1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onChange(item, position + 1);
                    viewHolder.price.setText(String.format(MyApp.getInstance().getString(R.string.book_item_price),
                            item.getPrice()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void removeAt(int position, Book book) {
        BookOrder.getInstance().removeItem(book);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, BookOrder.getInstance().getItems().size());
    }
}
