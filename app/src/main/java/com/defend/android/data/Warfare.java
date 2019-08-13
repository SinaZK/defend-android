package com.defend.android.data;

import org.json.JSONObject;

public class Warfare {
    private String name = "";
    private String body = "";
    private String imageUrl = "";
    private int category;

    private JSONObject object;

    public Warfare updateFromJson(JSONObject object) {
        this.object = object;

        name = object.optString("name");
        body = object.optString("body");
        imageUrl = object.optString("image_url");
        category = object.optInt("category");

        return this;
    }

    public boolean hasImage() {
        return imageUrl.length() > 0;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getCategory() {
        return category;
    }

    public JSONObject toJson() {
        return object;
    }
}
