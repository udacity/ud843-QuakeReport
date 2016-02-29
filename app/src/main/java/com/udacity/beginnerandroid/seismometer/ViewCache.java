package com.udacity.beginnerandroid.seismometer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * Cache of views in the grid item view to make recycling of views quicker. This avoids additional
 * {@link View#findViewById(int)} calls after the {@link ViewCache} is first created for a view. See
 * {@link EarthquakeAdapter#getView(int position, View convertView, ViewGroup parent)}.
 */
public class ViewCache {
    /**
     * View that displays the default magnitude of the Earthquake
     */
    public final TextView magnitudeView;


    public final TextView locationView;
    public final TextView locationDetailsView;
    public final TextView dateView;
    public final TextView timeView;

    /**
     * Constructs a new {@link ViewCache}.
     *
     * @param view which contains children views that should be cached.
     */
    public ViewCache(View view) {
        magnitudeView = (TextView) view.findViewById(R.id.feature_magnitude);
        locationView = (TextView) view.findViewById(R.id.feature_location);
        locationDetailsView = (TextView) view.findViewById(R.id.feature_location_details);
        dateView = (TextView) view.findViewById(R.id.feature_date);
        timeView = (TextView) view.findViewById(R.id.feature_time);
    }
}
