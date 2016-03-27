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
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<Earthquake> {

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
    private static final String rawUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=1&starttime=1992&endtime=1993&minmagnitude=7&minlongitude=-123&maxlongitude=-113&minlatitude=31&maxlatitude=36";

    private ConnectivityManager mConnectionManager;

    private TextView mUrlTextView;
    private TextView mJsonTextView;
    private TextView mMagnitudeTextView;
    private TextView mLocationTextView;
    private TextView mDateTextView;

    @Override
    public Loader<Earthquake> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Earthquake> loader, Earthquake data) {
        if (data != null) {
            mJsonTextView.setText(data.json);
            mMagnitudeTextView.setText(data.magnitude);
            mLocationTextView.setText(data.location);
            mDateTextView.setText(data.date);
        } else {
            String errorMessage = "No Earthquake Found";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, errorMessage);
        }
    }

    @Override
    public void onLoaderReset(Loader<Earthquake> loader) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mUrlTextView = (TextView) findViewById(R.id.url);
        mJsonTextView = (TextView) findViewById(R.id.json);
        mMagnitudeTextView = (TextView) findViewById(R.id.magnitude);
        mLocationTextView = (TextView) findViewById(R.id.location);
        mDateTextView = (TextView) findViewById(R.id.date_time);

        mConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mUrlTextView.setText(rawUrl);
        mJsonTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void updateEarthquakeData(View view) {
        try {
            new URL(rawUrl);
            NetworkInfo networkInfo = mConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                getSupportLoaderManager().initLoader(0, null, this);
            } else {
                String errorMessage = "No network connectivity!";
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                Log.e(LOG_TAG, errorMessage);
            }
        } catch (MalformedURLException e) {
            String errorMessage = "Looks like '" + rawUrl + " 'is not a valid URL.";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, errorMessage);
        }
    }

    public static class EarthquakeLoader extends AsyncTaskLoader<Earthquake> {

        public EarthquakeLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public Earthquake loadInBackground() {
            Earthquake earthquake = null;
            try {
                URL url = new URL(rawUrl);
                String json = QueryUtils.getJSONFromWeb(url);
                earthquake = QueryUtils.extractEarthquake(json);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Looks like '" + rawUrl + "' is not a valid URL.", e);
            }
            return earthquake;
        }
    }
}
