package com.example.android.quakereport;

public class earthquake_data_list {
   private String mMagniture;
   private String mPlace;
   private String mDate;

   public earthquake_data_list(String vMagnitude, String vPlace, String vDate){
       mMagniture = vMagnitude;
       mPlace = vPlace;
       mDate=vDate;
   }

   public String getMagnitude(){
     return mMagniture;
   }

    public String getPlace(){
        return mPlace;
    }

    public String getDate(){
        return mDate;
    }
}
