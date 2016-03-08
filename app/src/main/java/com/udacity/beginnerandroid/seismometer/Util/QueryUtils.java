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
import java.text.DateFormat;
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
        ArrayList<Earthquake> resultsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // TODO: Add these keys to strings.xml

            int featureArraySize = baseJsonResponse.getJSONArray("features").length();


            for (int i = 0; i < featureArraySize; i++) {
                JSONObject individualFeature = baseJsonResponse.getJSONArray("features")
                        .getJSONObject(i)
                        .getJSONObject("properties");

                Double magnitude = individualFeature.getDouble("mag");
                String rawLocation = individualFeature.getString("place");
                Long rawTime = individualFeature.getLong("time");
                String url = individualFeature.getString("url");

                String locationDetails, location;

                if (rawLocation.contains(" of")) {
                    String[] split = rawLocation.split(" of ");
                    locationDetails = split[0] + " of";
                    location = split[1];
                } else {
                    locationDetails = "Near The";
                    location = rawLocation;
                }

                String date = DateFormat.getDateInstance().format(rawTime);
                String time = DateFormat.getTimeInstance().format(rawTime);

                resultsList.add(new Earthquake(magnitude, locationDetails, location, time, date, url));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return resultsList;
    }
}
