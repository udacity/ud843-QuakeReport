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
import java.net.URL;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";

    // TODO: Add the base query url
    // Check out http://earthquake.usgs.gov/fdsnws/event/1/ for info!

    // TODO: Add constants for parameter keys
    // We need the format, eventtype, limit, and minmagnitude keys

    // TODO: Add constants for parameter values
    // We want the response format to be geojson.

    // We want the event type to be earthquakes only

    // We only want a single response

    // Set the min magnitude to whatever you want, but we went with 5

    // JSON parsing constants
    private static final String FEATURES_KEY = "features";
    private static final String PROPERTIES_KEY = "properties";
    private static final String MAGNITUDE_KEY = "mag";
    private static final String PLACE_KEY = "place";
    private static final String TIME_KEY = "time";

    /**
     * TODO: Complete the buildUrl() method
     *
     * In this method we're going to use the Uri.Builder class to add query parameters to a base
     * URL, much like we did using Postman. This saves us from having to fiddle with special
     * characters while we're composing our URL, and allows us to programmatically decide what
     * parameters to use add.
     *
     * You're probably wondering what a URI is, as opposed to a URL. A URL (uniform resource
     * locator) is a specific type of URI (uniform resource identifier) that specifies not only what
     * the resource _is_, but _how_ to retrieve it. To put it simply, a URL is a URI where the
     * scheme (the bit right at the start before the colon), is `http` or `https`.
     *
     * Once we've build up our URI, we now need to transform it into a URL.
     *
     * There's a problem that can happen here, though. All URLs are URIs, but not all URIs are URLs.
     * A URI could point to a file or some other type of resource that we can't get with an HTTP
     * request. The URL class has a constructor that takes a String, and one of two things can
     * happen: either the String contains a valid URL, and the URL object is constructed correctly,
     * or the String does not contain a valid URL... In which case we have a problem. The
     * constructor could just return `null`, but that's a recipe for very confusing behavior in code
     * way down the line. Fortunately, Java has a better solution.
     *
     * When a Java method can fail, we say it "throws an exception". That means that the usual
     * execution flow is interrupted, and the method doesn't return anything at all. Instead, it
     * packages up information about why it failed into an `Exception` object (or a subclass), and
     * "throws" it. It can then be "caught" by the method that called it (or the method that called
     * that method, and so on).
     *
     * So! When we're using a method that can throw an exception, we write code like the following
     *
     * <pre>
     * {@code
     *      try {
     *          someMethodThatCanFail()
     *      } catch (Exception e) {
     *          // Deal with the error, or at least log it.
     *      }
     * }
     * </pre>
     */

    public static URL buildUrl() {

        // TODO: Create a base URI to build upon, using Uri.Parse() on the base URL constant defined above

        // TODO: Create a Uri.Builder by calling buildUpon() on the Uri you just created

        // TODO: Append each of the four query parameters defined above using appendQueryParameter()

        // TODO: Create a new String holding the URI, using Uri.Builder.toString()

        URL url = null;

        // TODO: Open a try block

            // TODO: Initialize the URL with the String version of the URI we built

            // TODO: Catch a MalformedURLException
            // See the above javadoc for the syntax

            // TODO: Use Log.e() to log this class's LOG_TAG, an error message, and pass the exception

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
