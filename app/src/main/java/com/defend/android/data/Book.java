package com.defend.android.data;

import android.util.Log;

import org.json.JSONObject;

public class Book {
    private String title = "";
    private String body = "";
    private String author = "";
    private String translator = "";
    private String publishYear = "";
    private int price;
    private String imageUrl = "";
    private int id;
    private JSONObject object;

    public void updateFromJson(JSONObject object) {
        this.object = object;
        id = object.optInt("id");
        title = object.optString("title");
        body = object.optString("description");
        author = object.optString("author");
        translator = object.optString("translator");
        publishYear = object.optString("publish_year");
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

    public int getId() {
        return id;
    }

    public String getTranslator() {
        if (translator.equals("null")) {
            translator = "";
        }
        return translator;
    }

    public String getPublishYear() {
        return publishYear;
    }
}
