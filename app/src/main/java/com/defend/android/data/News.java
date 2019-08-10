package com.defend.android.data;

import org.json.JSONObject;

public class News {
    private String title = "";
    private String body = "";
    private String dateString = "";
    private String timeString = "";
    private String imageUrl = "";
    private String videoUrl = "";

    public void updateFromJson(JSONObject object) {
        title = object.optString("title");
        body = object.optString("body");
        dateString = object.optString("date");
        timeString = object.optString("time");
        imageUrl = object.optString("image_url");
        videoUrl = object.optString("video_url");
    }

    public boolean hasVideo() {
        return videoUrl.length() > 0;
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

    public String getDateTimeString() {
        return dateString + " - " + timeString;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
