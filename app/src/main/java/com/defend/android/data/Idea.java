package com.defend.android.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Idea {
    private String title = "";
    private String body = "";
    private String location = "";
    private String name = "";
    private String education = "";
    private String field = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    private String category = "";
    private String state = "";
    private String stateText = "";
    private String code = "";

    public void updateFromJson(JSONObject object) {
        title = object.optString("title");
        body = object.optString("body");
        location = object.optString("service_location");
        name = object.optString("name");
        education = object.optString("education_degree");
        field = object.optString("education_field");
        phone = object.optString("phone_number");
        email = object.optString("internet_address");
        address = object.optString("physical_address");
        category = object.optString("category");
        state = object.optString("state");
        stateText = object.optString("state_text");
        code = object.optString("code");
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        try {
            result.put("title", title);
            result.put("body", body);
            result.put("service_location", location);
            result.put("name", name);
            result.put("education_degree", education);
            result.put("education_field", field);
            result.put("phone_number", phone);
            result.put("internet_address", email);
            result.put("physical_address", address);
            result.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }


    public String getStateText() {
        return stateText;
    }

    public String getCode() {
        return code;
    }

}
