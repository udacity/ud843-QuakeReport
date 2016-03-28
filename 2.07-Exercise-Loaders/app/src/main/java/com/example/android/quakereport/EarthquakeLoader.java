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
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.quakereport.model.Earthquake;

public class EarthquakeLoader extends AsyncTaskLoader<Earthquake> {

    public static final String LOG_TAG = EarthquakeLoader.class.getName();

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

        // TODO: Log that the actual network access is happening

        // TODO: Get the Earthquake using QueryUtils
        // First get the query params map (QueryUtils.getParamMap())

        // Construct the URL (QueryUtils.buildUrl())

        // Retrieve the JSON (QueryUtils.getJSONFromWeb(url))

        // Parse the JSON and return the earthquake (QueryUtils.extractEarthquake(json))
        return null;
    }
}
