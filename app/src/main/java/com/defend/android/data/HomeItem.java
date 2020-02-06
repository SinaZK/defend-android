package com.defend.android.data;

import com.defend.android.R;

public class HomeItem {
    private String title = "";
    private int menuId = 0;
    private int drawable = R.drawable.ic_launcher_no_image;

    public HomeItem(String title, int menuId, int drawable) {
        this.title = title;
        this.menuId = menuId;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getDrawable() {
        return drawable;
    }
}
