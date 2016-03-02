package com.udacity.beginnerandroid.seismometer.Model;

import android.util.Log;

import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Earthquake {

    public static final String LOG_TAG = Earthquake.class.getName();

    final double mMagnitude;
    final String mLocation;
    final String mLocationDetails;
    final String mDate;
    final String mTime;
    final String mUrl;

    public Earthquake(double magnitude,
                      String location,
                      long time,
                      String url) {
        if (magnitude != 0) {
            this.mMagnitude = magnitude;
        } else {
            this.mMagnitude = 0.0;
        }

        Log.d(LOG_TAG, location);

        if (location != null) {
            if (location.contains(" of")) {
                String[] locationParts = extractStringDetails(location);
                this.mLocationDetails = locationParts[0];
                this.mLocation = locationParts[1];
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

        mDate = formatDate(time);
        mTime = formatTime(time);

        if (url != null) {
            //replace backslashes
            //this.mUrl = url.replace("\\/","/");
            this.mUrl = url;
        } else {
            // Defaulting to API Documentation
            this.mUrl = "http://earthquake.usgs.gov/fdsnws/event/1/";
        }

    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getLocationDetails() {
        return mLocationDetails;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getUrl() {
        return mUrl;
    }

    private String formatDate(long milliTime) {
        String formattedDate = "";
        if (milliTime != 0) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            formattedDate = dateFormat.format(milliTime);
        }
        return formattedDate;
    }

    private String formatTime(long milliTime) {
        String formattedTime = "";
        if (milliTime != 0) {
            DateFormat timeFormat = DateFormat.getTimeInstance();
            formattedTime = timeFormat.format(milliTime);
        }
        return formattedTime;
    }


    public String[] extractStringDetails(String description) {
        String[] details = new String[]{"", ""};

        Log.d(LOG_TAG, description);

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

        Log.d(LOG_TAG, details[0]);
        Log.d(LOG_TAG, details[1]);

        return details;
    }
}
