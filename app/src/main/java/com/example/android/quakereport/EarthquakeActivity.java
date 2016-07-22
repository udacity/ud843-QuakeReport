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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
        earthquakes.add(new EarthQuake("7.2","San Francisco","Feb 2,2016"));
        earthquakes.add(new EarthQuake("6.1","London","July 20,2015"));
        earthquakes.add(new EarthQuake("3.9","Tokyo","Nov 10,2014"));
        earthquakes.add(new EarthQuake("5.4","Mexico City","May 3,2014"));
        earthquakes.add(new EarthQuake("2.8","Moscow","Jan 31,2013"));
        earthquakes.add(new EarthQuake("4.9","Rio de Janeiro","Aug 19,2012"));
        earthquakes.add(new EarthQuake("1.6","Paris","Oct 30,2011"));

        // Create a new {@link EarthQuakeAdapter} to feed the list of earthquakes
        EarthQuakeAdapter earthQuakeAdapter = new EarthQuakeAdapter(this, earthquakes);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView earthquakeListView = (ListView) findViewById(R.id.earthquake_list);
        earthquakeListView.setAdapter(earthQuakeAdapter);
    }
}
