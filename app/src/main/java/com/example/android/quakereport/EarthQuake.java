package com.example.android.quakereport;

/**
 * Created by Anand Prabhu on 22/07/16.
 * {@link EarthQuake} represents the specific earth quake data
 * Each object has 3 properties: magnitude, location and date
 */
public class EarthQuake {

    // Magnitude of the earth quake
    private String mMagnitude;

    // Location of the specific earth quake
    private String mLocation;

    // Date when the earth quake occurred
    private String mDate;

    /**
     * Create a new EarthQuake object.
     *
     * @param vMagnitude is the magnitude of the earth quake
     * @param vLocation is the location of the specific earth quake
     * @param vDate is the date when earth quake occurred
     */
    public EarthQuake(String vMagnitude, String vLocation, String vDate) {
        this.mMagnitude = vMagnitude;
        this.mLocation = vLocation;
        this.mDate = vDate;
    }

    /**
     * Get the magnitude of the earth quake
     */
    public String getMagnitude() {
        return mMagnitude;
    }

    /**
     * Get the location of the earth quake
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Get the date when earth quake occurred
     */
    public String getDate() {
        return mDate;
    }
}
