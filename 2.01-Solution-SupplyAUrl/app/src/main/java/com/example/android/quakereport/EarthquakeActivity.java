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

import java.net.MalformedURLException;
import java.net.URL;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * TODO: Provide a URL
     *
     * To get up and running with the course code, the first exercise is super easy. Just supply a
     * URL that retrieves one or more earthquakes in geoJson format. If you don't have one lying
     * around from lesson 1, you can use:
     *
     * http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=1&starttime=1992&endtime=1993&minmagnitude=7&minlongitude=-123&maxlongitude=-113&minlatitude=31&maxlatitude=36
     *
     * Plug your URL into the rawUrl field right below this comment. Then build this project, and
     * deploy it to your test device. You should see the Query URL, and a "RUN QUERY" button. When
     * you hit the button, you should see the raw JSON returned, as well as the parsed magnitude,
     * location, and date. In the rest of this lesson, you'll build every piece of this pipeline,
     * until you've rebuilt this whole app from scratch!
     */
    private static final String RAW_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=1&starttime=1992&endtime=1993&minmagnitude=7&minlongitude=-123&maxlongitude=-113&minlatitude=31&maxlatitude=36";

    private URL mUrl;

    private TextView mUrlTextView;
    private TextView mJsonTextView;
    private TextView mMagnitudeTextView;
    private TextView mLocationTextView;
    private TextView mDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mUrlTextView = (TextView) findViewById(R.id.url);
        mJsonTextView = (TextView) findViewById(R.id.json);
        mMagnitudeTextView = (TextView) findViewById(R.id.magnitude);
        mLocationTextView = (TextView) findViewById(R.id.location);
        mDateTextView = (TextView) findViewById(R.id.date_time);

        mUrlTextView.setText(RAW_URL);

        mUrl = null;
        try {
            mUrl = new URL(RAW_URL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "");
        }

        mJsonTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void updateEarthquakeData(View view) {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (mUrl == null) {
            displayError(getString(R.string.error_bad_url));
        } else if (!manager.getActiveNetworkInfo().isConnected()) {
            displayError(getString(R.string.error_no_connection));
        } else {
            new EarthquakeTask().execute(mUrl);
        }
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, message);
    }


    private class EarthquakeTask extends AsyncTask<URL, Void, Earthquake> {

        @Override
        protected Earthquake doInBackground(URL... urls) {
            String json = QueryUtils.makeHttpRequest(urls[0]);
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
                displayError(getString(R.string.error_no_result));
            }
        }
    }
}
