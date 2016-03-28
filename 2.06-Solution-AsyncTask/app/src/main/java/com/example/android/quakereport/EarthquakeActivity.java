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


public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

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

                // TODO: Once you've completed EarthquakeTask below, create a new one
                EarthquakeTask earthquakeTask = new EarthquakeTask(this);

                // TODO: Call execute() on that EarthquakeTask, passing in the url
                earthquakeTask.execute(url);

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

    /**
     * TODO: Complete EarthquakeTask!
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a URL, and return an Earthquake. We won't do progress
     * updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground(), and onPostExecute().
     * As you hopefully read in the documentation, doInBackground() runs on a background thread, so
     * it can run long-running code (like network activity), without interfering with the
     * responsiveness of the app. Then `onPostExecute()` is passed the result of `doInBackground()`,
     * but runs on the UI thread, so it can use the produced data to update the UI. We'll also use
     * it to display a toast if something goes wrong.
     *
     * If you get stuck, check out the documentation here: http://developer.android.com/reference/android/os/AsyncTask.html
     *
     * Remember that the solution project is always available if you get stuck!
     */
    private class EarthquakeTask extends AsyncTask<URL, Void, Earthquake> {

        // TODO: Add a Context member variable, so we can make a toast later
        private final Context mContext;

        // TODO: Add a custom constructor that accepts and saves a Context
        // You can hit Ctrl-o to see a list of methods you can override
        public EarthquakeTask(Context context) {
            super();
            this.mContext = context;
        }

        @Override
        protected Earthquake doInBackground(URL... urls) {

            // TODO: Get the earthquake JSON by passing urls[0] into QueryUtils.getJSONFromWeb()
            String json = QueryUtils.getJSONFromWeb(urls[0]);

            // TODO: Use QueryUtils.extractEarthquake() to process the JSON and return an Earthquake
            return QueryUtils.extractEarthquake(json);
        }

        @Override
        protected void onPostExecute(Earthquake data) {
            if (data != null) {

                // TODO: Put the earthquake data into the UI TextViews
                // Populate mJsonTextView, mMagnitudeTextView, mLocationTextView, and mDateTextView
                mJsonTextView.setText(data.json);
                mMagnitudeTextView.setText(data.magnitude);
                mLocationTextView.setText(data.location);
                mDateTextView.setText(data.date);
            } else {

                // TODO: Display a toast stating that no earthquake was found
                // You'll need to use the Context we saved in the constructor
                // Scroll up to updateEarthquakeData() for a reminder on the syntax
                String errorMessage = "No Earthquake Found";
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
                Log.e(LOG_TAG, errorMessage);

            }
        }
    }

}
