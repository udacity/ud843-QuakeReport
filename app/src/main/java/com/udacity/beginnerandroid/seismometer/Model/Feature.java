package com.udacity.beginnerandroid.seismometer.Model;

import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

/**
 * Created by chrislei on 1/20/16.
 */
public class Feature {
    private double mMagnitude;
    private String mPlace;

    public Feature(double magnitude, String place) {
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
    }

    public double getMagnitude() { return mMagnitude; }
    public String getPlace() { return mPlace; }
}
