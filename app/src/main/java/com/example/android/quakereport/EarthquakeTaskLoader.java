package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

import static com.example.android.quakereport.Earthquake_LoaderActivity.LOG_TAG;

public class EarthquakeTaskLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {



    // getting the url for api calls
    String murl;

    public EarthquakeTaskLoader(@NonNull Context context, String url) {
        super(context);
        murl=url;
    }


    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"Loading");
        // to make android start doing background tasks
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {

        // this is the same code as in doInBakcground
        if (murl == null) {
            return null;
        }

//        Earthquake_LoaderActivity.load.setVisibility(View.VISIBLE);
//        Earthquake_LoaderActivity.pb.setVisibility(View.VISIBLE);

        Log.e(LOG_TAG,"Fetching data");
        // Don't perform the request if there are no URLs, or the first URL is null.
        return QueryUtils.fetchEarthquakeData(murl);


    }
}