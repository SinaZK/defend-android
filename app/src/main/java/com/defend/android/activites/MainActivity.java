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
import com.defend.android.fragments.BookShopFragment;
import com.defend.android.fragments.EventsFragment;
import com.defend.android.fragments.NewsFragment;
import com.defend.android.fragments.WarfareCategoryFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {
    String TAG = "_MAIN";
    Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        initDrawer();
        drawer.setSelection(Constants.MENU_BOOKS);
    }

    private void initDrawer() {
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_HOME).withName(R.string.menu_home)
                .withIcon(R.drawable.ic_menu_camera);
        PrimaryDrawerItem bookItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_BOOKS).withName(R.string.menu_books)
                .withIcon(R.drawable.ic_menu_gallery);
        PrimaryDrawerItem magazineItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_MAGAZINES).withName(R.string.menu_magazine)
            .withIcon(R.drawable.ic_menu_manage);
        PrimaryDrawerItem newsItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_NEWS).withName(R.string.menu_news)
                .withIcon(R.drawable.ic_menu_send);
        PrimaryDrawerItem eventItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_EVENTS).withName(R.string.menu_events)
                .withIcon(R.drawable.ic_menu_send);
        PrimaryDrawerItem warfareItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_WARFARE)
                .withName(R.string.menu_warfare).withIcon(R.drawable.ic_menu_send);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        homeItem,
                        new DividerDrawerItem(),
                        newsItem,
                        eventItem,
                        warfareItem,
                        bookItem,
                        magazineItem,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Fragment fragment = null;

                        if (drawerItem.getIdentifier() == Constants.MENU_NEWS) {
                            fragment = new NewsFragment();
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_EVENTS) {
                            fragment = new EventsFragment();
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_WARFARE) {
                            fragment = new WarfareCategoryFragment();
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_BOOKS) {
                            fragment = new BookShopFragment();
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

    @Override
    public void onBackPressed() {
        drawer.closeDrawer();
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        Fragment fragment = null;
//        Class fragmentClass = null;
//
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_books) {
//
//        } else if (id == R.id.nav_news) {
//            fragmentClass = NewsFragment.class;
//        } else if (id == R.id.nav_magazines) {
//
//        } else {
//            fragmentClass = NewsFragment.class;
//        }
//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
