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

import android.net.ConnectivityManager;
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

    private TextView mParamsTextView;
    private TextView mUrlTextView;
    private TextView mJsonTextView;
    private TextView mMagnitudeTextView;
    private TextView mLocationTextView;
    private TextView mDateTextView;

    private URL mUrl;

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

        Map<String, String> params = QueryUtils.getParamMap();
        if (params != null) {
            StringBuilder paramsViewContents = new StringBuilder();
            for (String param : params.keySet()) {

                paramsViewContents.append(param).append(": ").append(params.get(param)).append("\n");
            }
            mParamsTextView.setText(paramsViewContents.toString());
        } else {
            mParamsTextView.setText(getString(R.string.error_no_params));
        }

        mUrl = QueryUtils.buildUrl();
        if (mUrl != null) {
            mUrlTextView.setText(mUrl.toString());
        } else {
            mUrlTextView.setText(getString(R.string.error_bad_url));
        }

        mJsonTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void updateEarthquakeData(View view) {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (QueryUtils.getParamMap() == null) {
            displayError(getString(R.string.error_no_params));
        } else if (mUrl == null) {
            displayError(getString(R.string.error_bad_url));
        } else if (!manager.getActiveNetworkInfo().isConnected()) {
            displayError(getString(R.string.error_no_connection));
        } else {

            // TODO: Once you've completed EarthquakeTask below, create and execute an EarthquakeTask
            // new EarthquakeTask().execute(mUrl);

        }
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, message);
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

        @Override
        protected Earthquake doInBackground(URL... urls) {

            // TODO: Get the earthquake JSON by passing urls[0] into QueryUtils.getJSONFromWeb()

            // TODO: Use QueryUtils.extractEarthquake() to process the JSON and return an Earthquake
            return null;
        }

        @Override
        protected void onPostExecute(Earthquake data) {
            if (data != null) {

                // TODO: Put the earthquake data into the UI TextViews
                // Populate mJsonTextView, mMagnitudeTextView, mLocationTextView, and mDateTextView

            } else {

                // TODO: Use displayError() and getString() to display R.string.error_no_result

            }
        }
    }
}
