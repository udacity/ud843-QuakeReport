/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.quakereport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;
import java.util.Map;


/**
 * TODO: Implement Loader Callbacks
 *
 * Before you tackle this exercise, make sure you've finished the TODOs in EarthquakeLoader.java. A
 * little bit more is being left up to you in this file. You'll need to add the callbacks that allow
 * EarthquakeActivity to create an EarthquakeLoader and allow  EarthquakeLoader to deliver its
 * results.
 *
 * TODO: Declare that this activity implements LoaderCallbacks<Earthquake>
 *
 * TODO: Insert the required methods using Ctrl-i
 *
 * There are three required methods: onCreateLoader(), onLoadFinished(), and onLoaderReset()
 *
 * TODO: Complete onCreateLoader()
 *
 * In onCreateLoader(), simply return a new EarthquakeLoader(), passing in `this` as the context.
 *
 * TODO: Complete onLoadFinished()
 *
 * In onLoadFinished(), do exactly what we did in onPostExecute() in our AsyncTask, which has been
 * left at the bottom of this file for reference.
 *
 * TODO: Complete onLoaderReset()
 *
 * The onLoaderReset() method is called when the data in the loader should no longer be used. That
 * shouldn't happen in this app, but just in case, clear all the text views that we set in
 * onLoadFinished() by setting their text to an empty string.
 */

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    // TODO: Create an int constant for the earthquake loader id
    // It can be literally any int. This really only comes into play if you're using multiple loaders.

    private ConnectivityManager mConnectionManager;

    private TextView mParamsTextView;
    private TextView mUrlTextView;
    private TextView mJsonTextView;
    private TextView mMagnitudeTextView;
    private TextView mLocationTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mParamsTextView = (TextView) findViewById(R.id.params);
        mUrlTextView = (TextView) findViewById(R.id.url);
        mJsonTextView = (TextView) findViewById(R.id.json);
        mMagnitudeTextView = (TextView) findViewById(R.id.magnitude);
        mLocationTextView = (TextView) findViewById(R.id.location);
        mDateTextView = (TextView) findViewById(R.id.date_time);

        mConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Map<String, String> params = QueryUtils.getParamMap();

        StringBuilder paramsViewContents = new StringBuilder();
        if (params != null) {
            for (String param : params.keySet()) {
                paramsViewContents.append(param).append(": ").append(params.get(param)).append("\n");
            }
        }

        mParamsTextView.setText(paramsViewContents);

        URL url = QueryUtils.buildUrl(params);
        if (url != null) {
            mUrlTextView.setText(url.toString());
        } else {
            mUrlTextView.setText("null");
        }

        mJsonTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    public void updateEarthquakeData(View view) {

        NetworkInfo networkInfo = mConnectionManager.getActiveNetworkInfo();

        if (QueryUtils.getParamMap() == null) {
            String errorMessage = "Looks like no query parameters were supplied.";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, errorMessage);
            return;
        }

        URL url = QueryUtils.buildUrl(QueryUtils.getParamMap());

        if (url != null) {

            if (networkInfo != null && networkInfo.isConnected()) {

                // TODO: Log that a load has been requested

                // TODO: Get the LoaderManager
                // Use getSupportLoaderManager()

                // TODO: Initialize the loader
                // Use LoaderManager.initLoader(), passing in the int ID constant defined above
                // Pass null for the bundle, and this for the context.

            } else {
                String errorMessage = "No network connectivity!";
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e(LOG_TAG, errorMessage);
            }
        } else {
            String errorMessage = "The URL was not well formed!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, errorMessage);
        }

    }


    // Left for reference
    private class EarthquakeTask extends AsyncTask<URL, Void, Earthquake> {

        private final Context mContext;

        public EarthquakeTask(Context context) {
            super();
            this.mContext = context;
        }

        @Override
        protected Earthquake doInBackground(URL... urls) {
            String json = QueryUtils.getJSONFromWeb(urls[0]);
            return QueryUtils.extractEarthquake(json);
        }

        @Override
        protected void onPostExecute(Earthquake data) {
            if (data != null) {
                mJsonTextView.setText(data.json);
                mMagnitudeTextView.setText(data.magnitude);
                mLocationTextView.setText(data.location);
                mDateTextView.setText(data.date);
            } else {
                String errorMessage = "No Earthquake Found";
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                Log.e(LOG_TAG, errorMessage);

            }
        }
    }

}
