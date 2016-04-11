package com.example.android.quakereport.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.android.quakereport.R;
import com.example.android.quakereport.model.GeoCoordinate;

import java.util.HashMap;

public final class Utils {

    private static HashMap<String, GeoCoordinate> regionsMap;

    private Utils() {
    }

    public static int getMagnitudeColor(Context context, double magnitude) {
        int magnitudeColorId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorId = R.color.magnitude9;
                break;
            default:
                magnitudeColorId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(context, magnitudeColorId);
    }

    public static HashMap<String, GeoCoordinate> getRegionsMap(Context context) {
        if (regionsMap == null) {
            regionsMap = populateRegionsMap(context);
        }
        return regionsMap;
    }

    public static HashMap<String, GeoCoordinate> populateRegionsMap(Context context) {

        HashMap<String, GeoCoordinate> regionsMap = new HashMap<>();

        regionsMap.put(
                context.getString(R.string.settings_region_san_francisco_key),
                new GeoCoordinate(37.7749, -122.4194));
        regionsMap.put(
                context.getString(R.string.settings_region_los_angeles_key),
                new GeoCoordinate(34.0500, -118.2500));
        regionsMap.put(
                context.getString(R.string.settings_region_tokyo_key),
                new GeoCoordinate(35.7090, 139.7319));
        regionsMap.put(
                context.getString(R.string.settings_region_athens_key),
                new GeoCoordinate(37.9839, 23.7294));
        regionsMap.put(
                context.getString(R.string.settings_region_islamabad_key),
                new GeoCoordinate(33.7294, 73.0931));
        regionsMap.put(
                context.getString(R.string.settings_region_chengdu_key),
                new GeoCoordinate(30.5728, 104.0668));
        regionsMap.put(
                context.getString(R.string.settings_region_santiago_key),
                new GeoCoordinate(-33.4489, -70.6692));
        regionsMap.put(
                context.getString(R.string.settings_region_christchurch_key),
                new GeoCoordinate(-43.5320, 172.6362));
        regionsMap.put(
                context.getString(R.string.settings_region_tangier_key),
                new GeoCoordinate(35.7594, -5.8339));

        return regionsMap;
    }

}
