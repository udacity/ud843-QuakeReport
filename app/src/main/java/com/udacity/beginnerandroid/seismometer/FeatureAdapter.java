package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.udacity.beginnerandroid.seismometer.Model.Feature;

import java.util.ArrayList;

/**
 * Created by chrislei on 1/21/16.
 */
public class FeatureAdapter extends ArrayAdapter<Feature> {

    private Context mContext;
    private LayoutInflater mInflater;

    public FeatureAdapter(Context context, LayoutInflater inflater, ArrayList<Feature> features) {
        super(context, R.layout.list_item_feature, features);
        mContext = context;
        mInflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = mInflater.inflate(R.layout.list_item_feature, parent, false);
        }

        // Try to get view cache or create a new one if needed
        ViewCache viewCache = (ViewCache) result.getTag();
        if (viewCache == null) {
            viewCache = new ViewCache(result);
            result.setTag(viewCache);
        }

        // Fetch item
        final Feature earthquakeFeature = getItem(position);

        // Bind the data
        double magnitude = earthquakeFeature.getMagnitude();
        viewCache.magnitudeView.setText(String.format("%.1f", magnitude));

        // Alter the opacity of the circle background depending on the strength of quake
        GradientDrawable backgroundShape = (GradientDrawable) viewCache.magnitudeView.
                getBackground();

        if (Math.floor(magnitude) <= 2) {
            backgroundShape.setAlpha(77); // 30 % Opacity on 0 to 255(opaque) scale
        } else if (Math.floor(magnitude) <= 4) {
            backgroundShape.setAlpha(128); // 50 % Opacity on 0 to 255(opaque) scale
        } else if (Math.floor(magnitude) <= 6) {
            backgroundShape.setAlpha(179); // 70 % Opacity on 0 to 255(opaque) scale
        } else if (Math.floor(magnitude) <= 8) {
            backgroundShape.setAlpha(230); // 90 % Opacity on 0 to 255(opaque) scale
        } else {
            backgroundShape.setAlpha(255); // 100% Opacity on 0 to 255(opaque) scale
        }

        viewCache.locationView.setText(earthquakeFeature.getLocation());
        viewCache.locationDetailsView.setText(earthquakeFeature.getLocationDetails());
        viewCache.dateView.setText(earthquakeFeature.getDate());
        viewCache.timeView.setText(earthquakeFeature.getTime());

        return result;
    }

}
