package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.udacity.beginnerandroid.seismometer.Model.Feature;

import java.util.ArrayList;


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

        // Adjust the color of the circle background depending on the strength of quake
        GradientDrawable backgroundShape = (GradientDrawable) viewCache.magnitudeView.
                getBackground();

        switch((int) Math.floor(magnitude)) {
            case 0:
            case 1:
                backgroundShape.setColor(Color.parseColor("#4a7ba7"));
                break;
            case 2:
                backgroundShape.setColor(Color.parseColor("#04b4b3"));
                break;
            case 3:
                backgroundShape.setColor(Color.parseColor("#10cac9"));
                break;
            case 4:
                backgroundShape.setColor(Color.parseColor("#41d7b0"));
                break;
            case 5:
                backgroundShape.setColor(Color.parseColor("#f8cc4f"));
                break;
            case 6:
                backgroundShape.setColor(Color.parseColor("#f89828"));
                break;
            case 7:
                backgroundShape.setColor(Color.parseColor("#ec662a"));
                break;
            case 8:
                backgroundShape.setColor(Color.parseColor("#ec442a"));
                break;
            case 9:
                backgroundShape.setColor(Color.parseColor("#d93218"));
                break;
            default:
                backgroundShape.setColor(Color.parseColor("#c03823"));
                break;
        }

        viewCache.locationView.setText(earthquakeFeature.getLocation());
        viewCache.locationDetailsView.setText(earthquakeFeature.getLocationDetails());
        viewCache.dateView.setText(earthquakeFeature.getDate());
        viewCache.timeView.setText(earthquakeFeature.getTime());

        return result;
    }

}
