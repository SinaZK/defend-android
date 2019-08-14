package com.defend.android.constants;

public class Constants {
    public static String API_URL = "http://192.168.43.36:8000/";

    public static String API_SIGN_UP = "users/"; //POST
    public static String API_LOGIN = "users/login/"; //POST
    public static String API_LIST_NEWS = "news/"; //GET
    public static String API_LIST_MONTH_EVENTS = "events/"; //POST
    public static String API_LIST_ATLAS_CATEGORY = "atlas/with-category/%d"; //GET
    public static String API_LIST_BOOKS = "book/all/"; //POST

    //Font Constants
    public final static int FONT_REGULAR = 0;
    public final static int FONT_BOLD = 1;
    public final static int FONT_LIGHT = 2;

    //Data
    public static String DATA_FIELD_USERNAME = "username";
    public static String DATA_FIELD_TOKEN = "jwt-token";

    //Extras
    public static String EXTRA_LOGIN_USER = "username";
    public static String EXTRA_LOGIN_PASSWORD = "password";
    public static String EXTRA_NEWS_ID = "news";
    public static String EXTRA_EVENT_JSON = "event";
    public static String EXTRA_WARFARE_JSON = "warfare";

    //Main Drawer item id (just id not the order)
    public static int MENU_HOME = 1;
    public static int MENU_BOOKS = 2;
    public static int MENU_MAGAZINES = 3;
    public static int MENU_NEWS = 4;
    public static int MENU_EVENTS = 5;
    public static int MENU_WARFARE = 6;
}
