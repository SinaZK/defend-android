package com.defend.android.data;

import com.defend.android.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookOrder {
    private static BookOrder instance;

    private String state = Constants.STATE_CHECKOUT;
    private String billName = "";
    private String billPhone = "";
    private String billAddress = "";

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

        item.setQuantity(q);
    }

    public void removeItem(Book book) {
        BookShopItem item = find(book.getId());
        if (item == null) return;

        items.remove(item);
    }

    public JSONObject toJson(boolean includeState) {
        JSONObject object = new JSONObject();

        try {
            if(includeState) {
                object.put("state", state);
            }
            object.put("billing_address", billAddress);
            object.put("billing_name", billName);
            object.put("billing_phone", billPhone);

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

    public void addBillingInfo(String name, String phone, String address) {
        this.billName = name;
        this.billPhone = phone;
        this.billAddress = address;
    }

    public String getBillName() {
        return billName;
    }

    public String getBillPhone() {
        return billPhone;
    }

    public String getBillAddress() {
        return billAddress;
    }
}
