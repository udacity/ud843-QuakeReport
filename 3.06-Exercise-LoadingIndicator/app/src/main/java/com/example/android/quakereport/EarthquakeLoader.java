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
import android.util.Log;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.net.URL;
import java.util.List;

public class EarthquakeLoader extends android.content.AsyncTaskLoader<List<Earthquake>> {

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
    public List<Earthquake> loadInBackground() {

        // TODO: Note that we added a three second delay to ensure the progress bar is shown
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Log.d(LOG_TAG, e.getLocalizedMessage());
        }

        String json = QueryUtils.makeHttpRequest(mUrl);
        return QueryUtils.extractEarthquakes(json);
    }
}
