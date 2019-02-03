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
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;

public class Earthquake_LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    private static final String USGS_REQUEST_URL= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4&limit=100";
    private static final int EARTHQUAKE_LOADER_ID = 1;

    static TextView load ;
    static ProgressBar pb;
    Button retry;

    TextView empty_State_tv;

    public static final String LOG_TAG = EarthquakeAsyncTaskActivity.class.getName();

    earthquake_arrayAdpater mAdapter;
    ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);



        empty_State_tv = findViewById(R.id.empty_state);
        load=findViewById(R.id.loading);
        pb = findViewById(R.id.progress);
        retry = findViewById(R.id.retry);



        if(!isNetworkConnected()){
            load.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            empty_State_tv.setText("No Internet Connection Available");
            //retry.setVisibility(View.VISIBLE);
        }
//        else
//            retry.setVisibility(View.GONE);

//        if(isNetworkConnected() && !isInternetAvailable()){
//            empty_State_tv.setText("Connection has no Internet Access");
//        }


        // Find a reference to the {@link ListView} in the layout
        earthquakeListView= (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(empty_State_tv);


        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new earthquake_arrayAdpater(this, new ArrayList<Earthquake>());

        // the standerd way to start a loader
        getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID,null,this);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

//        retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID,null,
//                        Earthquake_LoaderActivity.this);
//            }
//        });


    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {

        Log.e(LOG_TAG,"Created Loader");
        // create and return a loader with the required url for api calls
        return new EarthquakeTaskLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        Log.e(LOG_TAG,"Fetched data");

        load.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }

        else if (!isNetworkConnected()){
            empty_State_tv.setText("No Internet Connection Available");
        }

        else if (isNetworkConnected() && earthquakes == null && earthquakes.isEmpty()){
            empty_State_tv.setText("No Earthquakes Found !!");
        }

    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        Log.e(LOG_TAG,"Reset Loader");

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }



}
