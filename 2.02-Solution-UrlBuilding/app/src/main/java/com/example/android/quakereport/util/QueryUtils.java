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

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";

    // TODO: Add the base query url
    // Check out http://earthquake.usgs.gov/fdsnws/event/1/ for info!
    private static final String BASE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?";

    // TODO: Add constants for parameter keys
    // We need the format, eventtype, limit, and minmagnitude keys
    private static final String FORMAT_PARAM = "format";
    private static final String EVENT_TYPE_PARAM = "eventtype";
    private static final String LIMIT_PARAM = "limit";
    private static final String MIN_MAGNITUDE_PARAM = "minmagnitude";

    // TODO: Add constants for parameter values
    // We want the response format to be geojson.
    private static final String FORMAT_VALUE = "geojson";

    // We want the event type to be earthquakes only
    private static final String EVENT_TYPE_VALUE = "earthquake";

    // We only want a single response
    private static final String LIMIT_VALUE = "1";

    // Set the min magnitude to whatever you want, but we went with 5
    public static final String MIN_MAGNITUDE_VALUE = "5";

    // JSON parsing constants
    private static final String FEATURES_KEY = "features";
    private static final String PROPERTIES_KEY = "properties";
    private static final String MAGNITUDE_KEY = "mag";
    private static final String PLACE_KEY = "place";
    private static final String TIME_KEY = "time";

    public static URL buildUrl() {

        // TODO: Create a base URI to build upon, using Uri.Parse() on the base URL constant defined above
        Uri baseUri = Uri.parse(BASE_URL);

        // TODO: Create a Uri.Builder by calling buildUpon() on the Uri you just created
        Builder uriBuilder = baseUri.buildUpon();

        // TODO: Append each of the four query parameters defined above using appendQueryParameter()
        uriBuilder.appendQueryParameter(FORMAT_PARAM, FORMAT_VALUE);
        uriBuilder.appendQueryParameter(EVENT_TYPE_PARAM, EVENT_TYPE_VALUE);
        uriBuilder.appendQueryParameter(LIMIT_PARAM, LIMIT_VALUE);
        uriBuilder.appendQueryParameter(MIN_MAGNITUDE_PARAM, MIN_MAGNITUDE_VALUE);

        // TODO: Create a new String holding the URI, using Uri.Builder.toString()
        String uriString = uriBuilder.toString();


        URL url = null;

        // TODO: Open a try block
        // Check this guide for help: https://docs.oracle.com/javase/tutorial/essential/exceptions/try.html
        try {

            // TODO: Initialize the URL with the String version of the URI we built
            url = new URL(uriString);

            // TODO: Catch a MalformedURLException
            // Check this guide for help https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
            // See the above javadoc for the syntax
        } catch (MalformedURLException e) {

            // TODO: Use Log.e() to log this class's LOG_TAG, an error message, and pass the exception
            Log.e(LOG_TAG, "Problem building the URL: ", e);
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
