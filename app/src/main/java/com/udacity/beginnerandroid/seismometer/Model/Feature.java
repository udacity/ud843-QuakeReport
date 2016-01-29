package com.udacity.beginnerandroid.seismometer.Model;

import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chrislei on 1/20/16.
 */
public class Feature {
    private double mMagnitude;
    private String mPlace;
    private long mTime;
    private String mUrl;

    public Feature(double magnitude,
                   String place,
                   long time,
                   String url) {
        if (magnitude != 0) {
            this.mMagnitude = magnitude;
        } else {
            this.mMagnitude = 0.0;
        }

        if (place != null) {
            this.mPlace = place;
        } else {
            this.mPlace = "Default Message, No Data";
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
    public String getPlace() { return mPlace; }
    public long getTime() { return mTime; }
    public String getUrl() { return mUrl; }

    public String getFormattedDate() {
        Date date = new Date(this.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mmZ");
        return sdf.format(date);
    }
}
