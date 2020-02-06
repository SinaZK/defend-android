package com.defend.android.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.defend.android.MyApp;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class CustomDownloadManager {
    private static CustomDownloadManager instance;
    private ArrayList <DownloadItem> downloadItems = new ArrayList<>();

    public static CustomDownloadManager getInstance() {
        if (instance == null) {
            instance = new CustomDownloadManager();
        }

        return instance;
    }

    public void addToDownload(String title, String desc, String url) {
        //File file = new File(MyApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), url.substring(10));
        File file = new File(MyApp.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), convertUrlToPath(url));

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
        downloadItems.add(item);
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            DownloadItem item = findItem(id);
            if (item != null) {
                openFile(item);
            }

            //Checking if the received broadcast is for our enqueued download by matching download id
            //Toast.makeText(MyApp.getInstance(), "Download Completed " + id, Toast.LENGTH_SHORT).show();
        }
    };

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

    private void openFile(DownloadItem item) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW);
        myIntent.setData(item.getDownloadPath());
        MyApp.getInstance().startActivity(myIntent);
    }
}
