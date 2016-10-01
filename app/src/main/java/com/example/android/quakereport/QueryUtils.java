package com.example.android.quakereport;

import android.util.Log;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Methods used to download data in the background
 * Created by Quinn on 9/11/16.
 */
public class QueryUtils {

    private static final String LOG_TAG = "QueryUtils";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<EarthquakeEvent> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        return extractEarthquakeData(jsonResponse);
    }

    /**
     * Helper class to convert String to URL
     * @param urlString
     * @return URL
     */
    public static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error: Invalid URL", e);
        }
        return url;
    }

    /**
     * Parse the JSON
     * Return a list of {@link EarthquakeEvent} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<EarthquakeEvent> extractEarthquakeData(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<EarthquakeEvent> earthquakeArray = new ArrayList<>();

        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonRootObject = new JSONObject(jsonResponse);
            JSONArray featuresArray = jsonRootObject.optJSONArray("features");
            // iterate through Array with key "features" in the JSON string
            for (int i = 0; i < featuresArray.length(); i++) {
                // get the object associated with index i
                JSONObject featureObject = featuresArray.getJSONObject(i);
                // get that object's properties using the JSONObject with key properties
                JSONObject property = featureObject.getJSONObject("properties");

                // get data from JSON to be displayed on screen
                double magnitude = property.optDouble("mag");
                String location = property.optString("place");
                long time = property.optLong("time");
                String url = property.optString("url");

                // format date
                Date dateObject = new Date(time);
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
                String dateToDisplay = dateFormat.format(dateObject);

                // format time
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");
                String timeToDisplay = timeFormat.format(dateObject);

                // create EarthquakeEvent object with data and add it to ArrayList earthquakes
                EarthquakeEvent currentEvent = new EarthquakeEvent(magnitude, location, dateToDisplay, timeToDisplay, url);
                earthquakeArray.add(currentEvent);

                // display list to verify that it is actually working or to catch any errors
                Log.i(LOG_TAG, "magnitude: " + magnitude +
                        "\nlocation: " + location +
                        "\ndate: " + dateToDisplay +
                        "\ntime: " + timeToDisplay +
                        "\nurl: " + url);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakeArray;
    }

    /**
     * Perform network request
     * @param url
     * @return String jsonResponse
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                // passes InputStream to a helper class that converts it to a String
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Problem with connection to specified URL" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with data connection", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert InputStream to a String
     * @param inputStream
     * @return
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
