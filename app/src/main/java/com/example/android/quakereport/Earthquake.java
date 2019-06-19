package com.example.android.quakereport;

public class Earthquake {

    //This is the variable name for the earthquake magnitude
    private String mMagnitude;

    //This is the variable name for the earthquake location
    private String mLocation;

    //This is the variable name for the earthquake date
    private String mDate;

    public Earthquake(String magnitude, String location, String date) {
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
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
