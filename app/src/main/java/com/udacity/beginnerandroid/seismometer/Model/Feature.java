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
    private long mTime;
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
            }
        } else {
            this.mLocation = "Default Message, No Data";
        }

        // From USGS Documentation
        // http://earthquake.usgs.gov/earthquakes/feed/v1.0/glossary.php#time
        // time is reported in milliseconds from the Epoch
        if (time != 0) {
            this.mTime = time;
        } else {
            this.mTime = 0;
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
    public long getTime() { return mTime; }
    public String getUrl() { return mUrl; }

    public String getFormattedDate() {
        Date date = new Date(this.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
        return sdf.format(date);
    }

    public String[] extractStringDetails(String description) {
        String[] details = new String[]{"",""};

        // Use a Regex Pattern To Extract Out Location Details
        Pattern p = Pattern.compile("(^.*of)(.*$)");
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
