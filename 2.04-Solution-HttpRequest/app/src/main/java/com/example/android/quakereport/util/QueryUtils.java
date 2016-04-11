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

    // TODO: Set a String constant for the HTTP method we'll use in our request
    // The method we want is "GET"
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

    /**
     * TODO: Declare that this function throws an IOException
     *
     * Check out https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html for
     * details on syntax.
     */
    public static String readInputStream(InputStream inputStream) throws IOException {

        // TODO: Create a new StringBuilder to hold our output
        StringBuilder output = new StringBuilder();

        // TODO: Make sure the inputStream is not null
        if (inputStream != null) {

            // TODO: Wrap inputStream into an InputStreamReader
            // We need to specify what character set the reader should decode the stream as
            // Pass Charset.forName("UTF-8") as the second arg to the InputStreamReader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // TODO: Wrap the InputStreamReader in a BufferedReader
            BufferedReader reader = new BufferedReader(inputStreamReader);

            // TODO: Read each line from the BufferedReader into our output StringBuilder
            // First load up a String called `line` with readLine()
            String line = reader.readLine();

            // Then while `line` is not null
            while (line != null) {

                // Append `line` to the output StringBuilder
                output.append(line);

                //Read another line into `line`
                line = reader.readLine();
            }
        }

        // TODO: Use toString() on our StringBuilder to return the result
        return output.toString();
    }

    public static String makeHttpRequest(URL url) {

        String output = "";

        // TODO: Create a new HttpURLConnection and set it to null for now
        HttpURLConnection urlConnection = null;

        // TODO: Open up a try block
        try {

            // TODO: Initialize your HttpURLConnection by calling url.openConnection()
            // You'll need to cast the result
            urlConnection = (HttpURLConnection) url.openConnection();

            // TODO: Use setRequestMethod() and the constant we defined above to set the HTTP request method
            urlConnection.setRequestMethod(HTTP_METHOD);

            // TODO: Call connect() on the HttpURLConnection
            urlConnection.connect();

            // TODO: Get the InputStream by calling getInputStream() on the connection
            InputStream inputStream = urlConnection.getInputStream();

            // TODO: Set the output String using the readInputStream() method we defined above
            output = readInputStream(inputStream);

            // TODO: Catch and log an IOException
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

            // TODO: Add a finally block, where if the HttpURLConnection is not null, call disconnect() on it
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
