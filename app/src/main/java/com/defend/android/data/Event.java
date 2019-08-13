package com.defend.android.data;

import org.json.JSONObject;

public class Event {
    private String title = "";
    private String body = "";
    private String location = "";
    private String date = "";
    private String time = "";
    private String imageUrl = "";

    public void updateFromJson(JSONObject object) {
        title = object.optString("title");
        body = object.optString("body");
        location = object.optString("location");
        date = object.optString("jalali_date");
        time = object.optString("time");
        imageUrl = object.optString("image_url");
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

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDay() {
        try {
            return Integer.valueOf(date.split("/")[2]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMonth() {
        try {
            return Integer.valueOf(date.split("/")[1]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getYear() {
        try {
            return Integer.valueOf(date.split("/")[0]);
        } catch (Exception e) {
            return -1;
        }
    }
}
