package com.defend.android.listeners;

import com.defend.android.data.BookShopItem;

public interface BookCartItemChangeListener {
    void onChange(BookShopItem bookShopItem, int quantity);
    void onItemRemove();
}
