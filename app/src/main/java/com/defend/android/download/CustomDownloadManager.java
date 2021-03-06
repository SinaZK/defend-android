package com.defend.android.download;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.MyJSON;
import com.defend.android.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class CustomDownloadManager {
    private static CustomDownloadManager instance;
    private ArrayList <DownloadItem> downloadItems = new ArrayList<>();
    private ArrayList <Runnable> onFinishRunnables = new ArrayList<>();

    public static CustomDownloadManager getInstance() {
        if (instance == null) {
            instance = new CustomDownloadManager();
        }

        return instance;
    }

    public CustomDownloadManager() {
        if (MyJSON.getData(MyApp.getInstance()) == null) {
            saveToFile();
        }
    }

    public DownloadItem addToDownload(String title, String desc, String url) {
        if (!permissionIsOk()) {
            return null;
        }

        if (isDuplicate(url)) {
            return null;
        }

        File file = new File(Environment.getExternalStorageDirectory().toString(), "Defend/" + convertUrlToPath(url));

        Log.i("_download", "" + file.getAbsoluteFile());

        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setTitle(title)// Title of the Download Notification
                .setDescription(desc)// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) MyApp.getInstance().getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.

        DownloadItem item = new DownloadItem();
        item.setId(downloadID);
        item.setUrl(url);
        item.setDownloadPath(Uri.fromFile(file));
        item.setState(Constants.DOWNLOAD_STATE_DOWNLOADING);
        downloadItems.add(item);

        saveToFile();

        Utils.showToast(MyApp.getInstance().getString(R.string.ebook_toast));

        return item;
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            DownloadItem item = findItem(id);
            if (item != null) {
                item.setState(Constants.DOWNLOAD_STATE_DOWNLOADED);
            }

            for (int i = 0;i < onFinishRunnables.size();i++) {
                if (onFinishRunnables.get(i) != null) {
                    onFinishRunnables.get(i).run();
                }
            }

            saveToFile();

            //Checking if the received broadcast is for our enqueued download by matching download id
            //Toast.makeText(MyApp.getInstance(), "Download Completed " + id, Toast.LENGTH_SHORT).show();
        }
    };

    private boolean isDuplicate(String url) {
        for (int i = 0;i < downloadItems.size();i++) {
            if (downloadItems.get(i).getUrl().equals(url)) {
                return true;
            }
        }

        return false;
    }

    public void onDestroy() {
        MyApp.getInstance().unregisterReceiver(onDownloadComplete);
    }

    public void register() {
        MyApp.getInstance().registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private String convertUrlToPath(String url) {
        url = url.replace("/", "");
        if (url.length() <= 15) {
            return url;
        }

        return url.substring(url.length() - 15);
    }

    private DownloadItem findItem(long id) {
        for(int i = 0;i < downloadItems.size();i++) {
            if(downloadItems.get(i).getId() == id) {
                return downloadItems.get(i);
            }
        }

        return null;
    }

    private boolean permissionIsOk() {
        int permissionWriteExternal = ContextCompat.checkSelfPermission(MyApp.getInstance(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    public ArrayList<DownloadItem> getDownloadItems() {
        return downloadItems;
    }

    public ArrayList<Runnable> getOnFinishRunnables() {
        return onFinishRunnables;
    }

    public void saveToFile() {
        JSONArray array = new JSONArray();

        for (int i = 0;i < downloadItems.size();i++) {
            array.put(downloadItems.get(i).toJson());
        }

        MyJSON.saveData(MyApp.getInstance(), array.toString());
    }

    public void updateFromFile() {
        try {
            downloadItems.clear();
            JSONArray array = new JSONArray(MyJSON.getData(MyApp.getInstance()));
            for(int i = 0;i < array.length();i++) {
                DownloadItem item = new DownloadItem();
                item.updateFromJson(array.getJSONObject(i));
                downloadItems.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
