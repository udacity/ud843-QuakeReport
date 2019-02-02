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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
////        earthquakes.add(new Earthquake("7.2","San Frasisco","Feb 2, 2016"));
////        earthquakes.add(new Earthquake("6.1","London","Feb 3, 2016"));
////        earthquakes.add(new Earthquake("3.9","Tokyo","Feb 4, 2016"));
////        earthquakes.add(new Earthquake("7.2","Mexico","Feb 5, 2016"));
////        earthquakes.add(new Earthquake("5.4","Moscow","Feb 6, 2016"));
////        earthquakes.add(new Earthquake("4.9","Rio de Janerio","Feb 6, 2016"));
////        earthquakes.add(new Earthquake("1.6","Paris","Feb 6, 2016"));

        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
////                this, android.R.layout.simple_list_item_1, earthquakes);
        earthquake_arrayAdpater mAdapter = new earthquake_arrayAdpater(this,earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


    }
}
