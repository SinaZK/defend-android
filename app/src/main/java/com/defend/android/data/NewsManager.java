package com.defend.android.data;

import org.json.JSONArray;

import java.util.ArrayList;

public class NewsManager {
    private static NewsManager instance;
    private ArrayList<News> news = new ArrayList<>();

    public static NewsManager getInstance() {
        if(instance == null) instance = new NewsManager();
        return instance;
    }

    public void clearNews() {
        news.clear();
    }

    public void addNews(JSONArray array) {
        for(int i = 0;i < array.length();i++) {
            News _new = new News();
            _new.updateFromJson(array.optJSONObject(i));
            news.add(_new);
        }
    }

    public ArrayList<News> getNews() {
        return news;
    }

}
