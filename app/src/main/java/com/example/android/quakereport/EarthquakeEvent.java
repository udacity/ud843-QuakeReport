package com.example.android.quakereport;

/**
 * Created by Quinn on 9/10/16.
 */
public class EarthquakeEvent {

    private double mMagnitude;
    private String mLocation;
    private String mDate;
    private String mTime;
    private String mURL;

    public EarthquakeEvent(double mag, String loc, String date, String time, String url) {
        mMagnitude = mag;
        mLocation = loc;
        mDate = date;
        mTime = time;
        mURL = url;
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

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }


    @Override
    public String toString() {
        return "mMagnitude: " + mMagnitude +
                ", mLocation: " + mLocation +
                ", mDate: " + mDate;
    }
}