package com.example.android.quakereport;

public class Earthquake {

    String mag;
    String location;
    String date;
    String time;
    String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    Earthquake(String mag, String loc, String date, String time, String url){
        this.mag = mag;
        this.location = loc;
        this.date = date;
        this.time =time;
        this.url = url;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
