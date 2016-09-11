package com.example.android.quakereport;

/**
 * Created by Quinn on 9/10/16.
 */
public class EarthquakeEvent {

    private double mMagnitude;
    private String mLocation;
    private String mDate;

    public EarthquakeEvent(double mag, String loc, String date) {
        mMagnitude = mag;
        mLocation = loc;
        mDate = date;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return "mMagnitude: " + mMagnitude +
                ", mLocation: " + mLocation +
                ", mDate: " + mDate;
    }
}