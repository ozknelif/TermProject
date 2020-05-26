package com.example.termproject;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;

public class CalEvent {

    private String name;
    private String info;
    private String startDate;
    private String endDate;
    private String location;
    private String startTime;
    private String endTime;
    private String repeatTime;
    private ArrayList<MyReminder> reminderList;

    public ArrayList<MyReminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(ArrayList<MyReminder> reminderList) {
        this.reminderList = reminderList;
    }

    public CalEvent(String name, String info, String startDate, String endDate, String location, String startTime, String endTime, String repeatTime) {

        this.name = name;
        this.info = info;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatTime = repeatTime;
        this.reminderList = new ArrayList<>();
    }

    public String reminderList(CalEvent event){
        StringBuilder sb = new StringBuilder();
        for (MyReminder rem: event.getReminderList()) {
            sb.append(rem.getTime()+"-"+rem.getDate()+",");
        }
        return sb.toString();
    }
    public CalEvent() {
        this.reminderList = new ArrayList<>();
    }

    public String getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(String repeatTime) {
        this.repeatTime = repeatTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
