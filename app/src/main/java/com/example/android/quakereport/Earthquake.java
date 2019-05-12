package com.example.android.quakereport;

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private String mDate;

    public Earthquake(String magnitude, String location, String date) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mDate = date;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }
}
