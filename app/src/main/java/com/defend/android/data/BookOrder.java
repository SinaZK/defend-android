package com.defend.android.data;

import android.util.Log;

import com.defend.android.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookOrder {
    private static BookOrder instance;

    private String state = Constants.STATE_CHECKOUT;
    private ArrayList <BookShopItem> items = new ArrayList<>();

    public static BookOrder getInstance() {
        if(instance == null) instance = new BookOrder();

        return instance;
    }

    public void clear() {
        items.clear();
    }

    public void addItem(Book book, int quantity) {
        BookShopItem item = find(book.getId());

        if(item == null) {
            BookShopItem shopItem = new BookShopItem(book, quantity);
            items.add(shopItem);
        }
    }

    private BookShopItem find(int bookId) {
        for(int i = 0;i < items.size();i++) {
            if (items.get(i).getBook().getId() == bookId) {
                return items.get(i);
            }
        }

        return null;
    }

    public void updateItem(Book book, int q) {
        BookShopItem item = find(book.getId());
        if(item == null) return;

        Log.i("salam", "set q : " + book.getTitle() + " : " + q);
        item.setQuantity(q);
    }

    public JSONObject toJson(boolean includeState) {
        JSONObject object = new JSONObject();

        try {
            if(includeState) {
                object.put("state", state);
            }
            JSONArray itemArray = new JSONArray();
            for(int i = 0;i < items.size();i++) {
                itemArray.put(items.get(i).toJson());
            }
            object.put("items", itemArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<BookShopItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        int price = 0;
        for(int i = 0;i < items.size();i++) {
            price += items.get(i).getPrice();
        }

        return price;
    }
}
