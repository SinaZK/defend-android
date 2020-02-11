package com.defend.android.data;

import org.json.JSONObject;

public class EBook {
    private String title = "";
    private String body = "";
    private String author = "";
    private int price;
    private String imageUrl = "";
    private String downloadUrl = "";
    private int id;
    private JSONObject object;
    private boolean hasPurchased;

    public void updateFromJson(JSONObject object) {
        this.object = object;
        id = object.optInt("id");
        title = object.optString("title");
        body = object.optString("description");
        author = object.optString("author");
        imageUrl = object.optString("image_url");
        downloadUrl = object.optString("file_url");
        price = object.optInt("price");
        hasPurchased = object.optBoolean("purchased");
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public boolean isHasPurchased() {
        return hasPurchased;
    }
}
