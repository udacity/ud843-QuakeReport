package com.example.android.quakereport.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.android.quakereport.R;

public final class Utils {

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

}
