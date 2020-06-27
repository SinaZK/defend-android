package com.defend.android.data;

import org.json.JSONObject;

public class WarfareCategory {
    private String name = "";
    private int id = 0;
    private String imageUrl = "";

    public WarfareCategory updateFromJson(JSONObject object) {
        name = object.optString("name");
        id = object.optInt("id");
        imageUrl = object.optString("image_url");
        return this;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }
}
