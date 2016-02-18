package com.udacity.beginnerandroid.seismometer.Model;


public class GeoCoordinate {
    public double mLatitude;
    public double mLongitude;

    public GeoCoordinate(double latitude, double longitude) {
        if (latitude != 0) {
            this.mLatitude = latitude;
        } else {
            this.mLatitude = 0.0;
        }

        if (longitude != 0) {
            this.mLongitude = longitude;
        } else {
            this.mLongitude = 0.0;
        }
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}



