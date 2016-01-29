package com.udacity.beginnerandroid.seismometer.Util;

import android.util.Log;

import com.udacity.beginnerandroid.seismometer.Model.Feature;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chrislei on 1/20/16.
 */
public class ParsingUtils {
    public final static String LOG_TAG = ParsingUtils.class.getSimpleName();

    //JSON related constants
    //final private static String BASE_QUERY_JSON = "query";

    public static ArrayList<Feature> extractFeatureArrayFromJson(String earthquakeJSON) {
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            int featureArraySize = baseJsonResponse.getJSONArray("features").length();

            ArrayList<Feature> resultsList = new ArrayList<Feature>();

            if (featureArraySize != 0) {
                for (int i = 0; i < featureArraySize; i++) {
                    JSONObject individualFeature = baseJsonResponse.getJSONArray("features")
                            .getJSONObject(i)
                            .getJSONObject("properties");

                    resultsList.add(new Feature(individualFeature.getDouble("mag"),
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

    public static Feature extractFeatureFromJson(String earthquakeJSON) {
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            JSONObject individualFeature = baseJsonResponse.getJSONArray("features")
                    .getJSONObject(0)
                    .getJSONObject("properties");

            double magnitude = individualFeature.getDouble("mag");
            String place  = individualFeature.getString("place");
            long time = individualFeature.getLong("time");
            String url = individualFeature.getString("url");

            return new Feature(magnitude, place, time, url);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
