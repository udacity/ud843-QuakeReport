package com.udacity.beginnerandroid.seismometer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chrislei on 1/21/16.
 *
 * Cache of views in the grid item view to make recycling of views quicker. This avoids
 * additional {@link View#findViewById(int)} calls after the {@link ViewCache} is first
 * created for a view. See
 * {@link FeatureAdapter#getView(int position, View convertView, ViewGroup parent)}.
 */
public class ViewCache {
    /**
     * View that displays the default magnitude of the Feature
     */
    public final TextView magnitudeView;

    /**
     * View that displays the Miwok translation of the word
     */
    public final TextView placeView;

    /**
     * Constructs a new {@link ViewCache}.
     *
     * @param view which contains children views that should be cached.
     */
    public ViewCache(View view) {
        magnitudeView = (TextView) view.findViewById(R.id.feature_magnitude);
        placeView = (TextView) view.findViewById(R.id.feature_place);
    }
}
