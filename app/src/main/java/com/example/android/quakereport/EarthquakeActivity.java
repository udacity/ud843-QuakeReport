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

        ArrayList<EarthquakeEvent> earthquakeEvents = new ArrayList<>();
        earthquakeEvents.add(new EarthquakeEvent(1.5, "San Francisco", "Jan 14, 1980"));
        earthquakeEvents.add(new EarthquakeEvent(2.5, "Los Angeles", "Jan 17, 1978"));
        earthquakeEvents.add(new EarthquakeEvent(3.5, "Seattle", "Feb 14, 1992"));
        earthquakeEvents.add(new EarthquakeEvent(4.5, "San Francisco", "March 15, 1965"));

        EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(this, earthquakeEvents);
        ListView listView = (ListView) findViewById(R.id.container);

        assert listView != null;
        listView.setAdapter(earthquakeListAdapter);
    }
}
