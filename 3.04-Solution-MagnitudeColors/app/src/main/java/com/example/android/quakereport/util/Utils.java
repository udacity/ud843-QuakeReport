package com.example.android.quakereport.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.android.quakereport.R;

public final class Utils {

    private Utils() {
    }

    public static int getMagnitudeColor(Context context, double magnitude) {

        // TODO: Declare an int to hold the ID of the proper color
        int magnitudeColorId;

        // TODO: Get the largest int less than magnitude using Math.floor() (you'll need to cast to an int)
        int magnitudeFloor = (int) Math.floor(magnitude);

        // TODO: Switch on the floor of the magnitude
        switch (magnitudeFloor) {
            // For case 0 and 1, set the color id to R.color.magnitude1
            case 0:
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            // For case 2, set the color id to R.color.magnitude2
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            // Handle cases 3--9
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
            // Add a default case and set the color id to R.color.magnitude10plus
            default:
                magnitudeColorId = R.color.magnitude10plus;
                break;
        }

        // TODO: Use ContextCompat.getColor() to get and return the appropriate color
        return ContextCompat.getColor(context, magnitudeColorId);
    }

}
