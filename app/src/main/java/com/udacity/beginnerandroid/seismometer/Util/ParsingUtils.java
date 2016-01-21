package com.udacity.beginnerandroid.seismometer.Util;

import android.util.Log;

import com.udacity.beginnerandroid.seismometer.Model.Feature;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chrislei on 1/20/16.
 */
public class ParsingUtils {
    public final static String LOG_TAG = ParsingUtils.class.getSimpleName();

    //JSON related constants
    //final private static String BASE_QUERY_JSON = "query";

    public static Feature extractFeatureFromJson(String earthquakeJSON) {
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            JSONObject individualFeature = baseJsonResponse.getJSONArray("features")
                    .getJSONObject(0)
                    .getJSONObject("properties");

            double magnitude = individualFeature.getDouble("mag");
            String place  = individualFeature.getString("place");

            return new Feature(magnitude, place);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
