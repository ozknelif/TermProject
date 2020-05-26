package com.example.termproject;

public class MyReminder {
    private String date;
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyReminder(String date, String time) {
        this.date = date;
        this.time = time;
    }
}
