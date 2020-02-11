package com.defend.android.download;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.defend.android.MyApp;

import org.json.JSONObject;

public class DownloadItem {
    private long id;
    private String url;
    private Uri downloadPath;

    private String title;
    private String imageUrl;

    private int state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Uri getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(Uri downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean hasImage() {
        return (imageUrl != null) && (!imageUrl.equals(""));
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void open() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myIntent.setDataAndType(getDownloadPath(), "application/pdf");
            MyApp.getInstance().startActivity(myIntent);
        } catch (Exception e) {
            Log.e("_download", e.toString());
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("url", url);
            object.put("downloadPath", downloadPath.toString());
            object.put("title", title);
            object.put("imageUrl", imageUrl);
            object.put("state", state);
        } catch (Exception e) {

        }

        return object;
    }

    public void updateFromJson(JSONObject object) {
        id = object.optInt("id");
        url = object.optString("url");
        downloadPath = Uri.parse(object.optString("downloadPath"));
        title = object.optString("title");
        imageUrl = object.optString("imageUrl");
        state = object.optInt("state");
    }
}
