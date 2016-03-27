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

import java.net.URL;
import java.util.Map;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<Earthquake> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private ConnectivityManager mConnectionManager;

    private TextView mParamsTextView;
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

        if (QueryUtils.buildUrl(QueryUtils.getParamMap()) != null) {

            if (networkInfo != null && networkInfo.isConnected()) {
                getSupportLoaderManager().initLoader(0, null, this);
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
            URL url = QueryUtils.buildUrl(QueryUtils.getParamMap());
            if (url != null) {
                String json = QueryUtils.getJSONFromWeb(url);
                earthquake = QueryUtils.extractEarthquake(json);
            }
            return earthquake;
        }
    }
}
