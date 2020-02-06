package com.defend.android.download;

import android.net.Uri;

public class DownloadItem {
    private long id;
    private String url;
    private Uri downloadPath;

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
}
