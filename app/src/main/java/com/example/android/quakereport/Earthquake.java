package com.example.android.quakereport;

import android.location.Location;

/**
 * Created by vijay on 12/5/2017.
 */

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mMilliSeconds;
    private String murl;

    public Earthquake(double magnitude, String Location, long  time,String url){
        murl=url;
        mMagnitude=magnitude;
        mLocation=Location;
        mMilliSeconds=time;
    }

    public double getMagnitude(){
        return mMagnitude;
    }
    public String getLocation(){
        return mLocation;
    }

    public long getTime(){
        return mMilliSeconds;
    }
    public String geturl(){
        return murl;
    }
}
