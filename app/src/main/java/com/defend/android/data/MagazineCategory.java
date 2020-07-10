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

    public JSONObject createJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("title", title);
            object.put("image_url", imageUrl);
        } catch (Exception e) {

        }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }
}
