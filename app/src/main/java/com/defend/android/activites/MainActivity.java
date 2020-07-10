package com.defend.android.activites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.customViews.MyToolbar;
import com.defend.android.data.MagazineCategory;
import com.defend.android.download.CustomDownloadManager;
import com.defend.android.fragments.BookShopFragment;
import com.defend.android.fragments.CreateEventFragment;
import com.defend.android.fragments.CreateThesisEventFragment;
import com.defend.android.fragments.EBookShopFragment;
import com.defend.android.fragments.EventsFragment;
import com.defend.android.fragments.HomeFragment;
import com.defend.android.fragments.IdeaFragment;
import com.defend.android.fragments.InfographicCategoryFragment;
import com.defend.android.fragments.MagazineCategoryFragment;
import com.defend.android.fragments.NewsFragment;
import com.defend.android.fragments.SearchFragment;
import com.defend.android.fragments.WarfareCategoryFragment;
import com.defend.android.utils.ResourceManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {
    String TAG = "_MAIN";
    public Drawer drawer;
    public MyToolbar toolbar;
    private String lastFragmentTag = "";

    Fragment fragment = null;
    android.app.Fragment supFragment = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setActivity(this);
        toolbar.setOnSearch(new Runnable() {
            @Override
            public void run() {
                setFragment(Constants.MENU_SEARCH);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        initDrawer();
        drawer.setSelection(Constants.MENU_HOME);

        requestStoragePermission();
    }

    private void initDrawer() {
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_HOME).withName(R.string.menu_home)
                .withIcon(R.drawable.home).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem bookItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_BOOKS).withName(R.string.menu_books)
                .withIcon(R.drawable.book2).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem magazineItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_MAGAZINES).withName(R.string.menu_magazine)
                .withIcon(R.drawable.magazine).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem havafazaMagazineItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_HAVAFAZA_MAG).withName(R.string.card_havafaza_mag)
                .withIcon(R.drawable.magazine).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem newsItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_NEWS).withName(R.string.menu_news)
                .withIcon(R.drawable.news2).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem eventItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_EVENTS).withName(R.string.menu_events)
                .withIcon(R.drawable.events).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem warfareItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_WARFARE)
                .withName(R.string.menu_warfare).withIcon(R.drawable.warfare).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem infoItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_INFOGRAPHIC)
                .withName(R.string.menu_infographic).withIcon(R.drawable.info_icon).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem tesCalendarItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_NEWEVENT)
                .withName(R.string.menu_tes_calendar).withIcon(R.drawable.add_event_icon).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem ideaItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_IDEA)
                .withName(R.string.menu_idea).withIcon(R.drawable.idea_minimal).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem ebookItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_EBOOK).withName(R.string.menu_ebook)
                .withIcon(R.drawable.ebook2).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem searchItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_SEARCH).withName(R.string.menu_search)
                .withIcon(R.drawable.search).withTypeface(ResourceManager.getInstance().getFont());
        PrimaryDrawerItem thesisItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_THESIS_EVENT)
                .withName(R.string.menu_thesis_event).withIcon(R.drawable.add_event_icon).withTypeface(ResourceManager.getInstance().getFont());


        PrimaryDrawerItem exitItem = new PrimaryDrawerItem().withIdentifier(Constants.MENU_EXIT)
                .withName(R.string.menu_exit).withIcon(R.drawable.exit_icon).withTypeface(ResourceManager.getInstance().getFont());

        drawer = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        homeItem,
                        new DividerDrawerItem(),
                        searchItem,
                        newsItem,
                        warfareItem,
                        infoItem,
                        new DividerDrawerItem(),
                        bookItem,
                        havafazaMagazineItem,
                        magazineItem,
                        ebookItem,
                        new DividerDrawerItem(),
                        eventItem,
                        tesCalendarItem,
                        thesisItem,
                        ideaItem,
                        new DividerDrawerItem(),
                        exitItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        fragment = null;

                        toolbar.setVisibility(View.VISIBLE);

                        if (drawerItem.getIdentifier() == Constants.MENU_EXIT) {
                            finish();
                            return true;
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_HOME) {
                            fragment = new HomeFragment();
                            ((HomeFragment) fragment).setMainActivity(MainActivity.this);
                            toolbar.setText(getString(R.string.menu_home));
                            toolbar.showSearch();
                        } else {
                            toolbar.hideSearch();
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_HAVAFAZA_MAG) {
                            MagazineCategory magazineCategory = new MagazineCategory();
                            magazineCategory.setId(1);
                            
                            Intent intent = new Intent(MyApp.getInstance(), MagazineListActivity.class);
                            intent.putExtra(Constants.EXTRA_MAGAZINE_JSON, magazineCategory.createJson().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApp.getInstance().startActivity(intent);
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_SEARCH) {
                            fragment = new SearchFragment();
                            ((SearchFragment) fragment).setActivity(MainActivity.this);
//                            toolbar.setText(getString(R.string.menu_search));
                            toolbar.setVisibility(View.GONE);
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_NEWS) {
                            fragment = new NewsFragment();
                            toolbar.setText(getString(R.string.menu_news));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_EVENTS) {
                            fragment = new EventsFragment();
                            ((EventsFragment) fragment).setActivity(MainActivity.this);
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

                        if (drawerItem.getIdentifier() == Constants.MENU_INFOGRAPHIC) {
                            fragment = new InfographicCategoryFragment();
                            toolbar.setText(getString(R.string.menu_infographic));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_IDEA) {
                            fragment = new IdeaFragment();
                            toolbar.setText(getString(R.string.menu_idea));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_MAGAZINES) {
                            fragment = new MagazineCategoryFragment();
                            toolbar.setText(getString(R.string.menu_magazine));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_THESIS_EVENT) {
                            if (!lastFragmentTag.equals("")) {
                                android.support.v4.app.Fragment fr_v4 = getSupportFragmentManager().findFragmentByTag(lastFragmentTag);
                                getSupportFragmentManager().beginTransaction().remove(fr_v4).commit();
                            }
                            lastFragmentTag = "";
                            supFragment = new CreateThesisEventFragment();
                            ((CreateThesisEventFragment) supFragment).setMainActivity(MainActivity.this);
                            getFragmentManager().beginTransaction().replace(R.id.flContent, supFragment).commit();
                            toolbar.setText(getString(R.string.menu_thesis_event));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_NEWEVENT) {
                            if (!lastFragmentTag.equals("")) {
                                android.support.v4.app.Fragment fr_v4 = getSupportFragmentManager().findFragmentByTag(lastFragmentTag);
                                getSupportFragmentManager().beginTransaction().remove(fr_v4).commit();
                            }
                            lastFragmentTag = "";
                            supFragment = new CreateEventFragment();
                            ((CreateEventFragment) supFragment).setMainActivity(MainActivity.this);
                            getFragmentManager().beginTransaction().replace(R.id.flContent, supFragment).commit();
                            toolbar.setText(getString(R.string.menu_tes_calendar));
                        }

                        if (drawerItem.getIdentifier() == Constants.MENU_EBOOK) {
                            fragment = new EBookShopFragment();
                            toolbar.setText(getString(R.string.menu_ebook));
                        }

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        if (fragment != null) {
                            lastFragmentTag = String.valueOf(drawerItem.getIdentifier());
                            if (supFragment != null) {
                                getFragmentManager().beginTransaction().remove(supFragment).commit();
                                supFragment = null;
                            }
                            fragmentManager.beginTransaction()
                                    .replace(R.id.flContent, fragment, lastFragmentTag).commit();
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

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            if (drawer.getCurrentSelection() == Constants.MENU_INFOGRAPHIC) {
                if (((InfographicCategoryFragment) fragment).queueSize() > 1) {
                    ((InfographicCategoryFragment) fragment).onBackPressed();
                } else {
                    setFragment(Constants.MENU_HOME);
                }
            } else if (drawer.getCurrentSelection() == Constants.MENU_WARFARE) {
                if (((WarfareCategoryFragment) fragment).queueSize() > 1) {
                    ((WarfareCategoryFragment) fragment).onBackPressed();
                } else {
                    setFragment(Constants.MENU_HOME);
                }
            } else if (drawer.getCurrentSelection() != Constants.MENU_HOME) {
                setFragment(Constants.MENU_HOME);
            } else if(drawer.getCurrentSelection() == Constants.MENU_HOME) {
                if (doubleBackToExitPressedOnce) {
                    finish();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        CustomDownloadManager.getInstance().onDestroy();
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        } else if(supFragment != null) {
            supFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    //Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
