package com.udacity.beginnerandroid.seismometer.util;

import android.util.Log;

import com.udacity.beginnerandroid.seismometer.model.Earthquake;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class QueryUtils {
    public final static String LOG_TAG = QueryUtils.class.getSimpleName();

    //JSON related constants
    //final private static String BASE_QUERY_JSON = "query";


    public static String getJSONFromWeb(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader;

        String earthquakeDataJSON;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Read the input Stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return "";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return "";
            }

            earthquakeDataJSON = buffer.toString();
            return earthquakeDataJSON;

        } catch (IOException e) {
            //  Log exception here
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return "";
    }


    public static ArrayList<Earthquake> extractFeatureArrayFromJson(String earthquakeJSON) {
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            int featureArraySize = baseJsonResponse.getJSONArray("features").length();

            ArrayList<Earthquake> resultsList = new ArrayList<Earthquake>();

            if (featureArraySize != 0) {
                for (int i = 0; i < featureArraySize; i++) {
                    JSONObject individualFeature = baseJsonResponse.getJSONArray("features")
                            .getJSONObject(i)
                            .getJSONObject("properties");

                    resultsList.add(new Earthquake(individualFeature.getDouble("mag"),
                            individualFeature.getString("place"),
                            individualFeature.getLong("time"),
                            individualFeature.getString("url")));
                }
            } else {
                // TODO - featureArraySize is Zero
                // TODO need to respond to this better
            }

            return resultsList;

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
