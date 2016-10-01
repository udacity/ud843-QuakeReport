package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Loader class that implements some of the abstract methods in AsyncTaskLoader
 * Created by Quinn on 9/25/16.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeEvent>> {

    private final String LOG_TAG = "EarthquakeLoader";
    private String urlString;


    /**
     * Constructor that takes the context
     * Override the super class constructor and pass in the app's existing context
     * @param context provides a new context to override the AsyncTaskLoader default constructor
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        urlString = url;
        Log.i(LOG_TAG, "overriding AsyncTaskLoader constructor with current context");

    }

    /**
     * ignore an existing data set (if one exists), and download a new one
     */
    @Override
    protected void onStartLoading() {
        if(isStarted()) {
            forceLoad();
            Log.i(LOG_TAG, "downloading a new data set in onStartLoading()");
        }

    }

    /**
     * get result of the work the background thread completed
     * in this case, it returns a list of EarthquakeEvents
     * @return List of EarthQuakes downloaded in background thread
     */
    @Override
    public List<EarthquakeEvent> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground started, will execute QueryUtility.fetchEarthquakeData to get data from server");
        return QueryUtils.fetchEarthquakeData(urlString);

    }

    // todo: check into deliverResults method to return the list of EarthquakeEvents to UI
}
