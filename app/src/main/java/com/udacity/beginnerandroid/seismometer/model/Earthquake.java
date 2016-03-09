package com.udacity.beginnerandroid.seismometer.model;

public class Earthquake {

    public final double mMagnitude;
    public final String mLocation;
    public final String mLocationDetails;
    public final String mDate;
    public final String mTime;
    public final String mUrl;

    public Earthquake(double magnitude,
                      String locationDetails,
                      String location,
                      String time,
                      String date,
                      String url) {

        this.mMagnitude = magnitude;
        this.mLocationDetails = locationDetails;
        this.mLocation = location;
        this.mDate = date;
        this.mTime = time;
        this.mUrl = url;
    }
}
