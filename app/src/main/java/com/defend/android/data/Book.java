package com.defend.android.data;

import org.json.JSONObject;

public class Book {
    private String title = "";
    private String body = "";
    private String author = "";
    private int price;
    private String imageUrl = "";
    private JSONObject object;

    public void updateFromJson(JSONObject object) {
        this.object = object;
        title = object.optString("title");
        body = object.optString("body");
        author = object.optString("author");
        imageUrl = object.optString("image_url");
        price = object.optInt("price");
    }

    public JSONObject toJson() {
        return object;
    }

    public boolean hasImage() {
        return imageUrl.length() > 0;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public int getPrice() {
        return price;
    }
}
