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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // TODO: Set a String constant for the HTTP method we'll use in our request
    // The method we want is "GET"

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

        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(FORMAT_PARAM, FORMAT_VALUE);
        paramMap.put(EVENT_TYPE_PARAM, EVENT_TYPE_VALUE);
        paramMap.put(LIMIT_PARAM, LIMIT_VALUE);
        paramMap.put(MIN_MAGNITUDE_PARAM, MIN_MAGNITUDE_VALUE);

        return paramMap;
    }

    public static URL buildUrl(Map<String, String> paramMap) {

        Builder uriBuilder = Uri.parse(BASE_URL).buildUpon();

        for (String param : paramMap.keySet()) {
            String value = paramMap.get(param);
            uriBuilder.appendQueryParameter(param, value);
        }

        URL url = null;
        try {
            url = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL: " + e.getLocalizedMessage());
        }
        return url;
    }

    /**
     * TODO: Declare that this function throws an IOException
     *
     * Check out https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html for
     * details on syntax.
     */
    public static String readInputStream(InputStream inputStream) {

        // TODO: Create a new StringBuilder to hold our output

        // TODO: Make sure the inputStream is not null

            // TODO: Wrap inputStream into an InputStreamReader

            // TODO: Wrap the InputStreamReader in a BufferedReader

            // TODO: Read each line from the BufferedReader into our output StringBuilder
            // First load up a String called `line` with readLine()

            // Then while `line` is not null

                // Append `line` to the output StringBuilder

                //Read another line into `line`

        // TODO: Use toString() on our StringBuilder to return the result
        return "";
    }

    public static String getJSONFromWeb(URL url) {

        String output = "";

        // TODO: Create a new HttpURLConnection and set it to null for now

        // TODO: Open up a try block

            // TODO: Initialize your HttpURLConnection by calling url.openConnection()
            // You'll need to cast the result

            // TODO: Use setRequestMethod() and the constant we defined above to set the HTTP request method

            // TODO: Call connect() on the HttpURLConnection

            // TODO: Get the InputStream by calling getInputStream() on the connection

            // TODO: Set the output String using the readInputStream() method we defined above

            // TODO: Catch and log an IOException

            // TODO: Add a finally block, where if the HttpURLConnection is not null, call disconnect() on it

        return output;
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
