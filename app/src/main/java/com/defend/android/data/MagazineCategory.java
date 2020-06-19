package com.defend.android.data;

import org.json.JSONObject;

public class MagazineCategory {
    private String title = "";
    private String imageUrl = "";
    private int id;
    private JSONObject object;

    public void updateFromJson(JSONObject object) {
        this.object = object;
        id = object.optInt("id");
        title = object.optString("title");
        imageUrl = object.optString("image_url");
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }

}
