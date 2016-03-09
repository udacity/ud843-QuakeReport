package com.udacity.beginnerandroid.seismometer.util;

import android.content.Context;
import android.util.Log;

import com.udacity.beginnerandroid.seismometer.R;
import com.udacity.beginnerandroid.seismometer.model.Earthquake;

import org.json.JSONArray;
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


    public static String getJSONFromWeb(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader;
        String output = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                output = buffer.toString();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return output;
    }


    public static ArrayList<Earthquake> extractFeatureArrayFromJson(String earthquakeJSON, Context context) {
        ArrayList<Earthquake> resultsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray(context.getString(R.string.json_features_key));

            for (int i = 0; i < featureArray.length(); i++) {
                JSONObject feature = featureArray.getJSONObject(i)
                        .getJSONObject(context.getString(R.string.json_properties_key));

                Double magnitude = feature.getDouble(context.getString(R.string.json_magnitude_key));
                String rawLocation = feature.getString(context.getString(R.string.json_place_key));
                Long rawTime = feature.getLong(context.getString(R.string.json_time_key));
                String url = feature.getString(context.getString(R.string.json_url_key));

                String locationDetails, location;

                if (rawLocation.contains(context.getString(R.string.place_details_divider))) {
                    String[] split = rawLocation.split(context.getString(R.string.place_details_divider));
                    locationDetails = split[0] + context.getString(R.string.place_details_of);
                    location = split[1];
                } else {
                    locationDetails = context.getString(R.string.place_details_near_the);
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
