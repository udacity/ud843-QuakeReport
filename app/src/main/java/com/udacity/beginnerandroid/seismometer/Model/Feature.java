package com.udacity.beginnerandroid.seismometer.Model;

import android.util.Log;

import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chrislei on 1/20/16.
 */
public class Feature {
    private double mMagnitude;
    private String mLocation;
    private String mLocationDetails;
    private String mDate;
    private String mTime;
    private String mUrl;

    public Feature(double magnitude,
                   String location,
                   long time,
                   String url) {
        if (magnitude != 0) {
            this.mMagnitude = magnitude;
        } else {
            this.mMagnitude = 0.0;
        }

        if (location != null) {
            if(location.contains(" of")) {
                String[] locationParts = extractStringDetails(location);
                this.mLocationDetails = locationParts[0];
                this.mLocation = locationParts[1];
            } else {
                this.mLocation = location;
                this.mLocationDetails = "Near The";
            }
        } else {
            this.mLocation = "Default Location";
            this.mLocationDetails ="Default Location Details";
        }

        // From USGS Documentation
        // http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#time
        // time is reported in milliseconds from the Epoch
        if (time != 0) {
            // Time Comes In As Unix Time.
            // Format As a String and Separate Date From Time
            separateDateFromTime(time);
        } else {
            this.mDate = "Default Date";
            this.mTime = "00:00";
        }

        if (url != null) {
            //replace backslashes
            //this.mUrl = url.replace("\\/","/");
            this.mUrl = url;
        } else {
            // Defaulting to API Documentation
            this.mUrl = "http://earthquake.usgs.gov/fdsnws/event/1/";
        }

    }

    public double getMagnitude() { return mMagnitude; }
    public String getLocation() { return mLocation; }
    public String getLocationDetails() { return mLocationDetails; }
    public String getDate() { return mDate; }
    public String getTime() { return mTime; }
    public String getUrl() { return mUrl; }

    private void separateDateFromTime(long time) {
        // Work With a formatted Date String
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a"); // Could specify PST
        String formattedDateFull = sdf.format(date);

        // Use a Regex Pattern To Extract Out Date From Time
        Pattern p = Pattern.compile("(^\\d{4}-\\d{2}-\\d{2})\\s+(.*$)");
        Matcher m = p.matcher(formattedDateFull);
        while (m.find()) { // Find each match in turn; String class can't do this.
            mDate = m.group(1); // Store Extracted Details Of Earthquake Location
            mTime = m.group(2); // Store Base Location

            /*// For Debugging Function
            Log.d("EARTHQUAKES", "Extracted this date: " + mDate);
            Log.d("EARTHQUAKES", "Extracted this time: " + mTime);
            */
        }
        return;
    }

    public String[] extractStringDetails(String description) {
        String[] details = new String[]{"",""};

        // Use a Regex Pattern To Extract Out Location Details
        Pattern p = Pattern.compile("(^.*of)\\s+(\\S{1,}.*$)");
        Matcher m = p.matcher(description);
        while (m.find()) { // Find each match in turn; String class can't do this.
            details[0] = m.group(1); // Store Extracted Details Of Earthquake Location
            details[1] = m.group(2); // Store Base Location

            /* For Debugging Function
            Log.d("EARTHQUAKES", "Extracted these details: " + details[0]);
            Log.d("EARTHQUAKES", "Extracted for base location: " + details[1]);
            */
        }

        return details;
    }
}
