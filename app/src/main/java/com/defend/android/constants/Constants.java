package com.defend.android.constants;

public class Constants {
    public static String API_URL = "http://192.168.43.36:8000/";

    public static String API_SIGN_UP = "users/"; //POST
    public static String API_LOGIN = "users/login/"; //POST
    public static String API_LIST_NEWS = "news/"; //GET

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

    //Main Drawer item id
    public static int MENU_HOME = 1;
    public static int MENU_BOOKS = 2;
    public static int MENU_MAGAZINES = 3;
    public static int MENU_NEWS = 4;
}
