package com.defend.android.data;

import org.json.JSONObject;

public class Infographic {
    private String name = "";
    private String topText = "";
    private String bottomText = "";
    private String imageUrl = "";
    private int category;

    private JSONObject object;

    public Infographic updateFromJson(JSONObject object) {
        this.object = object;

        name = object.optString("name");
        topText = object.optString("top_text");
        bottomText = object.optString("bottom_text");
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

    public String getTopText() {
        return topText;
    }

    public String getBottomText() {
        return bottomText;
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
