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

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;

public class EarthquakeLoader extends AsyncTaskLoader<Earthquake> {

    public static final String LOG_TAG = EarthquakeLoader.class.getName();

    private URL mUrl;

    public EarthquakeLoader(Context context, URL url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Earthquake loadInBackground() {

        // TODO: Log that the actual network access is happening
        Log.d(LOG_TAG, "Loading earthquake");

        // TODO: Retrieve the earthquake JSON using QueryUtils.makeHttpRequest() and mUrl
        String json = QueryUtils.makeHttpRequest(mUrl);

        // TODO: Return an Earthquake using QueryUtils.extractEarthquake()
        return QueryUtils.extractEarthquake(json);
    }
}
