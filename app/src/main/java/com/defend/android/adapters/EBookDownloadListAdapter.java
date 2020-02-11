package com.defend.android.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.activites.BookDetailActivity;
import com.defend.android.constants.Constants;
import com.defend.android.data.EBook;
import com.defend.android.download.CustomDownloadManager;
import com.defend.android.download.DownloadItem;
import com.defend.android.listeners.RecyclerLoadMoreListener;
import com.defend.android.utils.ResourceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EBookDownloadListAdapter extends RecyclerView.Adapter<EBookDownloadListAdapter.MyViewHolder> {

    private ArrayList<DownloadItem> downloadItems;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public Button actionButton;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            actionButton = view.findViewById(R.id.cart_btn);
            cardView = view.findViewById(R.id.parent);
        }
    }

    public void setDownloadItems(ArrayList<DownloadItem> downloadItems) {
        this.downloadItems = downloadItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(MyApp.getInstance())
                .inflate(R.layout.ebook_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final DownloadItem downloadItem = downloadItems.get(i);
        ResourceManager.getInstance().decorateTextView(viewHolder.title, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateButton(viewHolder.actionButton, Color.WHITE);
        viewHolder.actionButton.setBackgroundResource(R.drawable.btn_bg);
        viewHolder.title.setText(downloadItem.getTitle());

        if(downloadItem.hasImage()) {
            Picasso.get().load(downloadItem.getImageUrl())
                    .error(R.drawable.ic_launcher_no_image)
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.ic_launcher_no_image);
        }

        if (downloadItem.getState() == Constants.DOWNLOAD_STATE_NOT_START) {
            viewHolder.actionButton.setText(MyApp.getInstance().getString(R.string.ebook_downloads_download));
            //CustomDownloadManager.getInstance().addToDownload(ite);
        } else  if (downloadItem.getState() == Constants.DOWNLOAD_STATE_DOWNLOADING) {
            viewHolder.actionButton.setText(MyApp.getInstance().getString(R.string.ebook_downloads_downloading));
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else  if (downloadItem.getState() == Constants.DOWNLOAD_STATE_DOWNLOADED) {
            viewHolder.actionButton.setText(MyApp.getInstance().getString(R.string.ebook_downloads_open));
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadItem.open();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return downloadItems.size();
    }
}
