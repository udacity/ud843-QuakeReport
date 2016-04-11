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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;

// TODO: Declare that EarthquakeActivity implements LoaderCallbacks<List<Earthquake>>
public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<Earthquake> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private TextView mTextView;
    private URL mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mTextView = (TextView) findViewById(R.id.earthquakes);

        // TODO: Make the text view scrollable
        // Use setMovementMethod(new ScrollingMovementMethod())

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

    // TODO: Fix the signature of onCreateLoader (Loader<List<Earthquake>>)
    @Override
    public Loader<Earthquake> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, mUrl);
    }

    // TODO: Fix the type of the loader (Loader<List<Earthquake>>) and data (List<Earthquake>)
    @Override
    public void onLoadFinished(Loader<Earthquake> loader, Earthquake data) {
        if (data != null) {
            String magnitudeLabel = getString(R.string.label_magnitude);
            String dateLabel = getString(R.string.label_date);
            String locationLabel = getString(R.string.label_location);

            StringBuilder builder = new StringBuilder();

            // TODO: Repeat the following block for each Earthquake in data
            Earthquake earthquake = data;
            {
                builder.append(locationLabel).append(earthquake.location).append("\n");
                builder.append(dateLabel).append(earthquake.date).append("\n");
                builder.append(magnitudeLabel).append(earthquake.magnitude).append("\n\n");
            }
            mTextView.setText(builder.toString());
        } else {
            displayError(getString(R.string.error_no_result));
        }
    }

    // TODO: Fix the type of the loader (Loader<List<Earthquake>>)
    @Override
    public void onLoaderReset(Loader<Earthquake> loader) {
        mTextView.setText("");
    }

}
