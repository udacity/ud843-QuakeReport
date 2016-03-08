package com.udacity.beginnerandroid.seismometer.Model;

import java.text.DateFormat;


public class Earthquake {

    public static final String LOG_TAG = Earthquake.class.getName();

    public final double mMagnitude;
    public final String mLocation;
    public final String mLocationDetails;
    public final String mDate;
    public final String mTime;
    public final String mUrl;

    public Earthquake(double magnitude,
                      String location,
                      long time,
                      String url) {

        this.mMagnitude = magnitude;

        if (location != null) {
            if (location.contains(" of")) {
                String[] split = location.split(" of ");
                this.mLocationDetails = split[0] + " of";
                this.mLocation = split[1];
            } else {
                this.mLocation = location;
                this.mLocationDetails = "Near The";
            }
        } else {
            this.mLocation = "Default Location";
            this.mLocationDetails = "Default Location Details";
        }

        // From USGS Documentation
        // http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#time
        // time is reported in milliseconds from the Epoch

        mDate = DateFormat.getDateInstance().format(time);
        mTime = DateFormat.getTimeInstance().format(time);

        if (url != null) {
            this.mUrl = url;
        } else {
            // Defaulting to API Documentation
            this.mUrl = "http://earthquake.usgs.gov/fdsnws/event/1/";
        }
    }
}
