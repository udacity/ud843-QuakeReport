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
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeEvent>> {

    private EarthquakeListAdapter earthquakeListAdapter;
    public static final String LOG_TAG = "EarthquakeActivity";
    public static final String USGS_QUERY_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView emptyStateTextView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // set up ListView
        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list_view);
        // set up text view to display no earthquakes found
        emptyStateTextView = (TextView) findViewById(R.id.empty_state_text_view);
        /*
        set empty state text on ListView in case there is a problem with the connection or there
        are no earthquakes found in the url
         */
        earthquakeListView.setEmptyView(emptyStateTextView);
        //create adapter for ListView
        earthquakeListAdapter = new EarthquakeListAdapter(this, new ArrayList<EarthquakeEvent>());

        // set adapter to display list
        Log.i(LOG_TAG, "LOG: setting ListView adapter to display a list of earthquakes");
        earthquakeListView.setAdapter(earthquakeListAdapter);

        // set click listener for each item in the list
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get specific EarthquakeEvent for the item clicked
                EarthquakeEvent clickedEvent = earthquakeListAdapter.getItem(position);
                Uri webpage = Uri.parse(clickedEvent.getmURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        // display loading indicator in case there is a long load time
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // check network connection before downloading
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            // LoaderAsyncTask to get earthquake data from URL
            // define a new Loader; ID declared as private global variable
            Log.i(LOG_TAG, "LOG: getLoaderManager().initLoader is creating a new loader");
            getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // display error
            mProgressBar.setVisibility(GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * start Loader defined by id value
     * The return type of this method must match the declaration type of the AsyncTaskLoader class
     * in this case a List of EarthquakeEvents
     * @param id
     * @param args
     * @return EarthquakeLoader object
     */
    @Override
    public Loader<List<EarthquakeEvent>> onCreateLoader(int id, Bundle args) {
        // create loader for given URL
        Log.i(LOG_TAG, "LOG: executing the AsyncTaskLoader abstract class methods appropriate for this app");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    /**
     * Update UI with downloaded data using ArrayAdapter in updateUI method
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<List<EarthquakeEvent>> loader, List<EarthquakeEvent> data) {
        // turn off loading indicator
        mProgressBar.setVisibility(GONE);
        // update UI with result
        emptyStateTextView.setText(R.string.no_earthquakes_found);

        Log.i(LOG_TAG, "LOG: onFinishedLoader processes results from background thread and updates calls to update the UI");
        earthquakeListAdapter.clear();
        if(data != null && !data.isEmpty()) {
            earthquakeListAdapter.addAll(data);
        }
    }

    /**
     * reset data in loader by creating an empty array and passing it to the updateUI method
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<List<EarthquakeEvent>> loader) {
        Log.i(LOG_TAG, "LOG: onLoaderReset is clearing earthquakeListAdapter");
        earthquakeListAdapter.clear();
    }

}
