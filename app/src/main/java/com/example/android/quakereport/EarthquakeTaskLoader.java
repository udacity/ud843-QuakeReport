package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.quakereport.Earthquake;
import com.example.android.quakereport.QueryUtils;

import java.util.ArrayList;

public class EarthquakeTaskLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    // getting the url for api calls
    String murl;

    public EarthquakeTaskLoader(@NonNull Context context, String url) {
        super(context);
        murl=url;
    }


    @Override
    protected void onStartLoading() {
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

        // Don't perform the request if there are no URLs, or the first URL is null.
        return QueryUtils.fetchEarthquakeData(murl);
    }
}