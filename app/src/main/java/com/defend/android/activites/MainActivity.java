package com.defend.android.activites;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.MyToolbar;
import com.defend.android.fragments.BookShopFragment;
import com.defend.android.fragments.EventsFragment;
import com.defend.android.fragments.HomeFragment;
import com.defend.android.fragments.NewsFragment;
import com.defend.android.fragments.WarfareCategoryFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {
    String TAG = "_MAIN";
    public Drawer drawer;
    public MyToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        initDrawer();
        drawer.setSelection(Constants.MENU_HOME);
    }

    private void initDrawer() {
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_HOME).withName(R.string.menu_home)
                .withIcon(R.drawable.home);
        PrimaryDrawerItem bookItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_BOOKS).withName(R.string.menu_books)
                .withIcon(R.drawable.book2);
        PrimaryDrawerItem magazineItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_MAGAZINES).withName(R.string.menu_magazine)
            .withIcon(R.drawable.magazine);
        PrimaryDrawerItem newsItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_NEWS).withName(R.string.menu_news)
                .withIcon(R.drawable.news2);
        PrimaryDrawerItem eventItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_EVENTS).withName(R.string.menu_events)
                .withIcon(R.drawable.events);
        PrimaryDrawerItem warfareItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_WARFARE)
                .withName(R.string.menu_warfare).withIcon(R.drawable.warfare);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        homeItem,
                        new DividerDrawerItem(),
                        newsItem,
                        eventItem,
//                        warfareItem,
                        bookItem,
//                        magazineItem,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Fragment fragment = null;
                        toolbar.setVisibility(View.VISIBLE);

                        if (drawerItem.getIdentifier() == Constants.MENU_HOME) {
                            fragment = new HomeFragment();
                            ((HomeFragment) fragment).setMainActivity(MainActivity.this);
                            toolbar.setText(getString(R.string.menu_home));
                            toolbar.setVisibility(View.GONE);
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_NEWS) {
                            fragment = new NewsFragment();
                            toolbar.setText(getString(R.string.menu_news));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_EVENTS) {
                            fragment = new EventsFragment();
                            toolbar.setText(getString(R.string.menu_events));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_WARFARE) {
                            fragment = new WarfareCategoryFragment();
                            toolbar.setText(getString(R.string.menu_warfare));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_BOOKS) {
                            fragment = new BookShopFragment();
                            toolbar.setText(getString(R.string.menu_books));
                        }

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        if(fragment != null) {
                            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                        }

                        drawer.closeDrawer();

                        return true;
                    }
                })
                .withDrawerWidthDp(250)
                .build();
    }

    public void setFragment(int type) {
        drawer.setSelection(type);
    }

    @Override
    public void onBackPressed() {
        drawer.closeDrawer();
    }

}
