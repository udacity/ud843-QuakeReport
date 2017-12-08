package com.example.android.quakereport;

import android.location.Location;

/**
 * Created by vijay on 12/5/2017.
 */

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private String mTime;

    public Earthquake(String magnitude, String Location, String  time){
        mMagnitude=magnitude;
        mLocation=Location;
        mTime=time;
    }

    public String getMagnitude(){
        return mMagnitude;
    }
    public String getLocation(){
        return mLocation;
    }

    public String getTime(){
        return mTime;
    }
}
