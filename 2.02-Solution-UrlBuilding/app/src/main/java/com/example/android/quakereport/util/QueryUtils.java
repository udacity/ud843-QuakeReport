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

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";

    // TODO: Add the base query url
    // Check out http://earthquake.usgs.gov/fdsnws/event/1/ for info!
    private static final String BASE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    // TODO: Add constants for query parameter keys
    // We need the format, eventtype, limit, and minmagnitude keys
    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_EVENT_TYPE = "eventtype";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_MIN_MAGNITUDE = "minmagnitude";

    // TODO: Add constants for query parameter values
    // We want the response format to be geojson.
    private static final String VALUE_FORMAT = "geojson";

    // We want the event type to be earthquakes only (Search the API docs (linked above) for "eventtype"
    private static final String VALUE_EVENT_TYPE = "earthquake";

    // We only want a single response
    private static final String VALUE_LIMIT = "1";

    // Set the min magnitude to whatever you want, but we went with 5
    private static final String VALUE_MIN_MAGNITUDE = "5";

    private static final String KEY_FEATURES = "features";
    private static final String KEY_PROPERTIES = "properties";
    private static final String KEY_MAGNITUDE = "mag";
    private static final String KEY_PLACE = "place";
    private static final String KEY_TIME = "time";

    private QueryUtils() {
    }

    public static URL buildUrl() {

        // TODO: Create a base URI to build upon, using Uri.Parse() on the base URL constant defined above
        Uri baseUri = Uri.parse(BASE_URL);

        // TODO: Create a Uri.Builder by calling buildUpon() on the Uri you just created
        Builder uriBuilder = baseUri.buildUpon();

        // TODO: Append each of the four query parameters defined above using appendQueryParameter()
        uriBuilder.appendQueryParameter(PARAM_FORMAT, VALUE_FORMAT);
        uriBuilder.appendQueryParameter(PARAM_EVENT_TYPE, VALUE_EVENT_TYPE);
        uriBuilder.appendQueryParameter(PARAM_LIMIT, VALUE_LIMIT);
        uriBuilder.appendQueryParameter(PARAM_MIN_MAGNITUDE, VALUE_MIN_MAGNITUDE);

        // TODO: Create a new String holding the URI, using Uri.Builder.toString()
        String uriString = uriBuilder.toString();

        URL url = null;

        // TODO: Open a try block
        // Check this guide for help: https://docs.oracle.com/javase/tutorial/essential/exceptions/try.html
        try {

            // TODO: Initialize the URL with the String version of the URI we built
            url = new URL(uriString);

            // TODO: Catch a MalformedURLException
            // Check this guide for help: https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
            // You can also check out the README.md at the project root for syntax help
        } catch (MalformedURLException e) {

            // TODO: Use Log.e() to log this class's LOG_TAG, an error message, and pass the exception
            Log.e(LOG_TAG, "Problem building the URL: ", e);
        }

        // TODO: Consider that this function either returns a URL, or returns null...
        // It'll be important to check that the URL returned is not null before we try to do anything with it
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

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray(KEY_FEATURES);
            JSONObject firstFeature = featureArray.getJSONObject(0);
            JSONObject properties = firstFeature.getJSONObject(KEY_PROPERTIES);

            Double magnitude = properties.getDouble(KEY_MAGNITUDE);
            String location = properties.getString(KEY_PLACE);
            Long rawTime = properties.getLong(KEY_TIME);

            earthquake = new Earthquake(magnitude, location, rawTime, earthquakeJSON);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return earthquake;
    }
}
