package com.defend.android.data;

import org.json.JSONException;
import org.json.JSONObject;

public class BookShopItem {
    private Book book;
    private int quantity;


    public BookShopItem() {
    }

    public BookShopItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("book", book.getId());
            object.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        return quantity * book.getPrice();
    }
}
