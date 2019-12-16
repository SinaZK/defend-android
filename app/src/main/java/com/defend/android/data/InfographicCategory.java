package com.defend.android.data;

import org.json.JSONObject;

public class InfographicCategory {
    private String name = "";
    private int id = 0;

    public InfographicCategory updateFromJson(JSONObject object) {
        name = object.optString("name");
        id = object.optInt("id");
        return this;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
