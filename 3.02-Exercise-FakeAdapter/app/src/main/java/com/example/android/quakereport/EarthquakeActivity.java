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

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private LinearLayout mEarthquakeLinearLayout;
    private URL mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mEarthquakeLinearLayout = (LinearLayout) findViewById(R.id.earthquakes);

        mUrl = QueryUtils.buildUrl();
        updateEarthquakeData();
    }

    public void updateEarthquakeData() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (QueryUtils.getParamMap() == null) {
            displayError(getString(R.string.error_no_params));
        } else if (mUrl == null) {
            displayError(getString(R.string.error_bad_url));
        } else if (!manager.getActiveNetworkInfo().isConnected()) {
            displayError(getString(R.string.error_no_connection));
        } else {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, message);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        if (data != null) {

            // TODO: Create a LayoutInflater using LayoutInflater.from(this)

            for (Earthquake earthquake : data) {

                // TODO: Use the inflater to inflate R.layout.earthquake_list_item
                // Pass mEarthquakeLinearLayout as the root, but don't attach the new view yet
                // That means pass false for the third argument to inflate

                // TODO: Find the magnitude, location_offset, location, and date TextViews

                // TODO: Populate the magnitude, location, and date fields

                // TODO: Check if the earthquake.locationOffset field is empty

                    // TODO: If so, set the locationOffset TextView's contents to R.string.location_near_the

                    // TODO: Create the locationOffset string using String.format()
                    // The first argument should be Locale.getDefault()
                    // Then pass the string that we're filling in: getString(R.string.location_of)
                    // Finally pass earthquake.locationOffset

                    // TODO: Set the locationOffset TextView's contents to the string we just created

                // TODO: Use addView() to add our new list item to mEarthquakeLinearLayout

            }
        } else {
            displayError(getString(R.string.error_no_result));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {

        // TODO: Handle the loader being reset by calling removeAllViews() on our linear layout

    }
}
