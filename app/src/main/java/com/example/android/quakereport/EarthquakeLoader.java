package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private static final String TAG = "EarthquakeLoader";
    private static String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        // Finish implementing this constructor
        if (url.length() != 0 )
            mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading: started");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(TAG, "loadInBackground: started");
        // Implement this method
        if(mUrl == null) return null;

        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}
