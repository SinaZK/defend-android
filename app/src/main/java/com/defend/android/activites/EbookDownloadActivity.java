package com.defend.android.activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.defend.android.R;
import com.defend.android.adapters.EBookDownloadListAdapter;
import com.defend.android.customViews.ActivityToolbar;
import com.defend.android.download.CustomDownloadManager;

public class EbookDownloadActivity extends Activity {

    ActivityToolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_download);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        toolbar.setActivity(this);
        toolbar.setText(getString(R.string.ebook_downloads_title));

        initRV();
    }


    EBookDownloadListAdapter adapter;
    private void initRV() {
        adapter = new EBookDownloadListAdapter();
        adapter.setDownloadItems(CustomDownloadManager.getInstance().getDownloadItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomDownloadManager.getInstance().getOnFinishRunnables().add(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomDownloadManager.getInstance().updateFromFile();
    }
}
