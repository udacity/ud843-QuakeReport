package com.example.android.quakereport;

public class earthquake_data_list {
   private double mMagniture;
   private String mPlace;
   private long mInMilliSeconds;

   public earthquake_data_list(double vMagnitude, String vPlace, long vDate){
       mMagniture = vMagnitude;
       mPlace = vPlace;
       mInMilliSeconds=vDate;
   }

   public double getMagnitude(){
     return mMagniture;
   }

    public String getPlace(){
        return mPlace;
    }

    public long getDate(){
        return mInMilliSeconds;
    }
}
