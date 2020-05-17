package com.example.termproject;

import java.util.Date;

public class CalEvent {

    private String name;
    private String info;
    private String startDate;
    private String endDate;
    private String location;

    public CalEvent(String name, String info, String startDate, String endDate, String location) {
        this.name = name;
        this.info = info;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
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
