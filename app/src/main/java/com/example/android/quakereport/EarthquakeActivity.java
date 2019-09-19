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
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    ListView earthquakeListView;
    TextView empty;
    ProgressBar spinner;
    /**
     * Adapter for the list of earthquakes
     */
    private MyEQadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empty = findViewById(R.id.empty);

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = findViewById(R.id.list);
        spinner = findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.VISIBLE);

        /**
         * Get the network info
         * @param context
         */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm != null ? cm.getActiveNetworkInfo() : null;

        if (network != null && network.isConnectedOrConnecting()) {
            //If there is a connection, initialize the Loader
            Log.i("EarthquakeActivity", "onCreate() started & Loader is Force-initialized!");
            getLoaderManager().initLoader(1, null, this).forceLoad();
        } else {
            empty.setText(R.string.NoConnection);
            earthquakeListView.setEmptyView(empty);
        }

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new MyEQadapter(this, R.layout.earthquake_activity, new LinkedList<Earthquake>());

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake eq = (Earthquake) mAdapter.getItem(i);
                Intent url = new Intent(Intent.ACTION_VIEW);
                url.setData(Uri.parse(eq.getUrl()));
                startActivity(url);
            }
        });

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i("EarthquakeActivity", "Loader is created");
        return new EarthQuakeAsyncTaskLoader(EarthquakeActivity.this, USGS);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        Log.i("EarthquakeActivity", "spinning stopped because data is displayed on screen");
        spinner.setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty())
            mAdapter.addAll(earthquakes);
        else
            empty.setText(R.string.emptyData);
        earthquakeListView.setEmptyView(empty);
        Log.i("EarthquakeActivity", "Loader has finished loading. Updating UI");
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.i("EarthquakeActivity", "Loader is reset and the activity is closed");
    }

    public static class EarthQuakeAsyncTaskLoader extends AsyncTaskLoader<List<Earthquake>> {
        private String mUrl;

        public EarthQuakeAsyncTaskLoader(Context context, String url) {
            super(context);
            mUrl = url;
            Log.i("EarthquakeActivity", "AsyncTaskLoader constructor called");
        }

        @Override
        public List<Earthquake> loadInBackground() {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (mUrl == null) {
                return null;
            }
            Log.i("EarthquakeActivity", "data is loading on background thread and fetched");
            return Queryutils.fetchEarthquakeData(USGS);
        }
    }
}
