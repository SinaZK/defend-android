package com.defend.android.data;

import org.json.JSONException;
import org.json.JSONObject;

public class BookShopItem {
    private int bookId;
    private int quantity;
    private int price;

    public BookShopItem() {
    }

    public BookShopItem(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("book", bookId);
            object.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int q) {
        this.quantity += q;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
