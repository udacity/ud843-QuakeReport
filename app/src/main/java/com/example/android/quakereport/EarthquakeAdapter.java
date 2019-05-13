package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if existing view is being reused, otherwise inflate.
        View listItemView = convertView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        double magnitude = currentEarthquake.getMagnitude();
        magnitudeView.setText(formatMagnitude(magnitude));

        String locationString = currentEarthquake.getLocation();
        TextView offsetView = listItemView.findViewById(R.id.offset);
        TextView locationView = listItemView.findViewById(R.id.location);

        if (locationString.contains(LOCATION_SEPARATOR)) {
            // split string into two parts
            String[] split = locationString.split(LOCATION_SEPARATOR);
            offsetView.setText(split[0] + LOCATION_SEPARATOR);
            locationView.setText(split[1]);

        } else {
            offsetView.setText(R.string.near_the);
            locationView.setText(locationString);
        }


        long timeInMilliseconds = currentEarthquake.getTimeInMilliseconds();
        Date dateObject = new Date(timeInMilliseconds);

        TextView date = listItemView.findViewById(R.id.date);

        date.setText(formatDate(dateObject));

        TextView time = listItemView.findViewById(R.id.time);

        time.setText(formatTime(dateObject));

        return listItemView;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(magnitude);
    }


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("H:MM a");
        return timeFormat.format(dateObject);
    }
}
