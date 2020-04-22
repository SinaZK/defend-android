package com.defend.android.data;

import android.util.Log;

import com.defend.android.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {
    private String title = "";
    private String body = "";
    private String location = "";
    private String date = "";
    private String time = "";
    private String endDate = "";
    private String endTime = "";
    private String info = "";
    private String imageUrl = "";
    private String type = "";
    private String studentName = "";
    private String teacherName = "";
    private String university = "";

    public void updateFromJson(JSONObject object) {
        title = object.optString("title");
        body = object.optString("body");
        location = object.optString("location");
        date = object.optString("jalali_date");
        time = object.optString("time");
        endDate = object.optString("jalali_end_date");
        endTime = object.optString("end_time");
        info = object.optString("info");
        imageUrl = object.optString("image_url");
        type = object.optString("type");
        studentName = object.optString("student_name");
        teacherName = object.optString("teacher_name");
        university = object.optString("university");
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        try {
            result.put("title", title);
            result.put("body", body);
            result.put("location", location);
            result.put("jalali_date", date);
            result.put("time", time);
            result.put("jalali_end_date", endDate);
            result.put("end_time", endTime);
            result.put("info", info);
            result.put("image_url", imageUrl);
            result.put("type", type);
            result.put("student_name", studentName);
            result.put("teacher_name", teacherName);
            result.put("university", university);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean hasImage() {
        return imageUrl.length() > 0;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        if (time.split(":").length > 2) {
            return time.split(":")[0] + ":" + time.split(":")[1];
        }

        return time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDay() {
        try {
            return Integer.valueOf(date.split("/")[2]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMonth() {
        try {
            return Integer.valueOf(date.split("/")[1]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getYear() {
        try {
            return Integer.valueOf(date.split("/")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getInfo() {
        return info;
    }

    public String getTypeString() {
        return type;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getUniversity() {
        return university;
    }

    public boolean isRegular() {
        return type.equals(Constants.EVENT_TYPE_REGULAR);
    }

    public boolean isThesis() {
        return type.equals(Constants.EVENT_TYPE_THESIS);
    }

    public void setRegular() {
        type = Constants.EVENT_TYPE_REGULAR;
    }

    public void setThesis() {
        type = Constants.EVENT_TYPE_THESIS;
    }
}
