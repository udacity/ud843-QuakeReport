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

package com.example.android.quakereport.util;

import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import com.example.android.quakereport.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";
    private static final String BASE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?";
    // Query Parameters
    private static final String FORMAT_PARAM = "format";
    private static final String EVENT_TYPE_PARAM = "eventtype";
    private static final String LIMIT_PARAM = "limit";
    private static final String MIN_MAGNITUDE_PARAM = "minmagnitude";
    // Query Parameter Values
    private static final String FORMAT_VALUE = "geojson";
    private static final String EVENT_TYPE_VALUE = "earthquake";
    private static final String LIMIT_VALUE = "1";
    private static final String MIN_MAGNITUDE_VALUE = "5";
    // JSON parsing constants
    private static final String FEATURES_KEY = "features";
    private static final String PROPERTIES_KEY = "properties";
    private static final String MAGNITUDE_KEY = "mag";
    private static final String PLACE_KEY = "place";
    private static final String TIME_KEY = "time";


    public static Map<String, String> getParamMap() {

        // TODO: Declare a new HashMap<String, String>
        HashMap<String, String> paramMap = null;

        // TODO: Use paramMap.put() to put each of the four query parameters into the map

        return paramMap;
    }

    public static URL buildUrl(Map<String, String> paramMap) {

        Builder uriBuilder = Uri.parse(BASE_URL).buildUpon();

        // TODO: User paramMap.keySet() to iterate over all the parameters

            // TODO: For each parameter, use paramMap.get() to get the corresponding parameter value

            // TODO: Append the query parameter to the uriBuilder

        URL url = null;
        try {
            url = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL: " + e.getLocalizedMessage());
        }
        return url;
    }

    public static String getJSONFromWeb(URL url) {
        StringBuilder output = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();

                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return output.toString();
    }

    public static Earthquake extractEarthquake(String earthquakeJSON) {

        Earthquake earthquake = null;

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray(FEATURES_KEY);
            JSONObject firstFeature = featureArray.getJSONObject(0);
            JSONObject properties = firstFeature.getJSONObject(PROPERTIES_KEY);

            Double magnitude = properties.getDouble(MAGNITUDE_KEY);
            String location = properties.getString(PLACE_KEY);
            Long rawTime = properties.getLong(TIME_KEY);

            earthquake = new Earthquake(magnitude, location, rawTime, earthquakeJSON);


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return earthquake;
    }
}
