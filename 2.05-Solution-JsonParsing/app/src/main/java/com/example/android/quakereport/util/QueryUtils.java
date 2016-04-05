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
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";
    private static final String BASE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_EVENT_TYPE = "eventtype";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_MIN_MAGNITUDE = "minmagnitude";

    private static final String VALUE_FORMAT = "geojson";
    private static final String VALUE_EVENT_TYPE = "earthquake";
    private static final String VALUE_LIMIT = "1";
    private static final String VALUE_MIN_MAGNITUDE = "5";

    // TODO: Add constants for the JSON keys we're interested in
    // We need "features", "properties", "mag", "place", and "time"
    private static final String KEY_FEATURES = "features";
    private static final String KEY_PROPERTIES = "properties";
    private static final String KEY_MAGNITUDE = "mag";
    private static final String KEY_PLACE = "place";
    private static final String KEY_TIME = "time";

    private QueryUtils() {
    }

    public static Map<String, String> getParamMap() {
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(PARAM_FORMAT, VALUE_FORMAT);
        paramMap.put(PARAM_EVENT_TYPE, VALUE_EVENT_TYPE);
        paramMap.put(PARAM_LIMIT, VALUE_LIMIT);
        paramMap.put(PARAM_MIN_MAGNITUDE, VALUE_MIN_MAGNITUDE);

        return paramMap;
    }

    public static URL buildUrl() {
        Uri baseUri = Uri.parse(BASE_URL);
        Builder uriBuilder = baseUri.buildUpon();

        Map<String, String> paramMap = getParamMap();
        if (paramMap != null) {
            for (String param : paramMap.keySet()) {
                String value = paramMap.get(param);
                uriBuilder.appendQueryParameter(param, value);
            }
        }

        String uriString = uriBuilder.toString();
        URL url = null;
        try {
            url = new URL(uriString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL: ", e);
        }
        return url;
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) {
        String output = "";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            output = readInputStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return output;
    }

    public static Earthquake extractEarthquake(String earthquakeJSON) {

        Earthquake earthquake = null;

        // TODO: Open a try block
        try {

            // TODO: Create the root object using JSONObject(earthquakeJSON)
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // TODO: Get the features array by calling getJSONArray() on the root JSONObject
            JSONArray featureArray = baseJsonResponse.getJSONArray(KEY_FEATURES);

            // TODO: Get the first JSONObject in the features array
            JSONObject firstFeature = featureArray.getJSONObject(0);

            // TODO: Get the properties object
            JSONObject properties = firstFeature.getJSONObject(KEY_PROPERTIES);

            // TODO: Use getDouble() to get the magnitude
            Double magnitude = properties.getDouble(KEY_MAGNITUDE);

            // TODO: Use getString() to get the location
            String location = properties.getString(KEY_PLACE);

            // TODO: Use getLong() to get the raw time
            Long rawTime = properties.getLong(KEY_TIME);

            // TODO: Set earthquake to a new earthquake constructed from the extracted data
            earthquake = new Earthquake(magnitude, location, rawTime, earthquakeJSON);

            // TODO: Catch and log a JSONException
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        
        return earthquake;
    }
}
