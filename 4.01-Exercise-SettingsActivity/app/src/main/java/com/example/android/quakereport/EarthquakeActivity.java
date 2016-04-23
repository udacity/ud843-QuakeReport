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
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private ListView mEarthquakeListView;

    private View mEmptyView;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    private EarthquakeAdapter mEarthquakeAdapter;
    private URL mUrl;

    private OnItemClickListener mEarthquakeClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Earthquake earthquake = mEarthquakeAdapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, earthquake.uri);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mEarthquakeListView = (ListView) findViewById(R.id.earthquakes);
        mEmptyView = findViewById(R.id.empty_view);
        mErrorTextView = (TextView) findViewById(R.id.error);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mEarthquakeAdapter = new EarthquakeAdapter(this);
        mEarthquakeListView.setAdapter(mEarthquakeAdapter);
        mEarthquakeListView.setOnItemClickListener(mEarthquakeClickListener);
        mEarthquakeListView.setEmptyView(mEmptyView);

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
        mProgressBar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(message);
        Log.e(LOG_TAG, message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // TODO: Use getMenuInflater().inflate() to inflate R.menu.main

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            // TODO: Create an explicit intent to launch SettingsActivity

            // TODO: Call startActiity() passing in the intent

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        mEarthquakeAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mEarthquakeAdapter.addAll(data);
        } else {
            displayError(getString(R.string.error_no_result));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mEarthquakeAdapter.clear();
    }
}
