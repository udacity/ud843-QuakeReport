package com.udacity.beginnerandroid.seismometer.Model;

/**
 * Created by chrislei on 1/20/16.
 */
public class Feature {
    private double mMagnitude;
    private String mPlace;

    public Feature(double magnitude, String place) {
        this.mMagnitude = magnitude;
        this.mPlace = place;
    }

    public double getMagnitude() { return mMagnitude; }
    public String getPlace() { return mPlace; }
}
