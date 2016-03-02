package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.beginnerandroid.seismometer.Model.Earthquake;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Let's build an adaptor, to get our data from our Array of earthquakes into the list view in our activity!
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    // TODO: Move click listener back here
    // TODO: Should these be final?
    private Context mContext;
    private LayoutInflater mInflater;

    public EarthquakeAdapter(Context context, LayoutInflater inflater, ArrayList<Earthquake> earthquakes) {
        super(context, R.layout.earthquake_list_item, earthquakes);
        mContext = context;
        mInflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = mInflater.inflate(R.layout.earthquake_list_item, parent, false);
        }

        // Try to get view cache or create a new one if needed
        ViewCache viewCache = (ViewCache) result.getTag();
        if (viewCache == null) {
            viewCache = new ViewCache(result);
            result.setTag(viewCache);
        }

        // Fetch item
        final Earthquake earthquakeFeature = getItem(position);

        // Bind the data
        double magnitude = earthquakeFeature.getMagnitude();
        viewCache.magnitudeView.setText(String.format(Locale.US, "%.1f", magnitude));

        // Adjust the color of the circle background depending on the strength of quake
        GradientDrawable magnitudeCircle = (GradientDrawable) viewCache.magnitudeView.
                getBackground();


        int magnitudeColorId;

        switch((int) Math.floor(magnitude)) {
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

        final int magnitudeColor = ContextCompat.getColor(mContext, magnitudeColorId);
        magnitudeCircle.setColor(magnitudeColor);

        viewCache.locationView.setText(earthquakeFeature.getLocation());
        viewCache.locationDetailsView.setText(earthquakeFeature.getLocationDetails());
        viewCache.dateView.setText(earthquakeFeature.getDate());
        viewCache.timeView.setText(earthquakeFeature.getTime());

        return result;
    }

    /**
     *
     * Cache of views in the grid item view to make recycling of views quicker. This avoids additional
     * {@link View#findViewById(int)} calls after the {@link ViewCache} is first created for a view. See
     * {@link EarthquakeAdapter#getView(int position, View convertView, ViewGroup parent)}.
     */
    public static class ViewCache {
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
}
