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
 * Let's build an adaptor, to get our data from our Array of earthquakes into the list view in our
 * activity!
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private final Context mContext;
    private final LayoutInflater mInflater;

    public EarthquakeAdapter(Context context, LayoutInflater inflater, ArrayList<Earthquake> earthquakes) {
        super(context, R.layout.earthquake_list_item, earthquakes);
        mContext = context;
        mInflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.earthquake_list_item, parent, false);
        }

        // Try to get view cache or create a new one if needed
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        // Fetch item
        Earthquake earthquake = getItem(position);

        // Bind the data
        double magnitude = earthquake.getMagnitude();
        String roundedMagnitude = String.format(Locale.US, "%.1f", magnitude);
        viewHolder.magnitudeView.setText(roundedMagnitude);
        viewHolder.locationView.setText(earthquake.getLocation());
        viewHolder.locationDetailsView.setText(earthquake.getLocationDetails());
        viewHolder.dateView.setText(earthquake.getDate());
        viewHolder.timeView.setText(earthquake.getTime());

        // Adjust the color of the circle background depending on the strength of quake
        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.magnitudeView.
                getBackground();

        int magnitudeColorId;

        switch ((int) Math.floor(magnitude)) {
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



        return view;
    }


    class ViewHolder {

        public final TextView magnitudeView;
        public final TextView locationView;
        public final TextView locationDetailsView;
        public final TextView dateView;
        public final TextView timeView;

        public ViewHolder(View view) {
            magnitudeView = (TextView) view.findViewById(R.id.feature_magnitude);
            locationView = (TextView) view.findViewById(R.id.feature_location);
            locationDetailsView = (TextView) view.findViewById(R.id.feature_location_details);
            dateView = (TextView) view.findViewById(R.id.feature_date);
            timeView = (TextView) view.findViewById(R.id.feature_time);
        }
    }
}
