package com.example.android.quakereport;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String TAG = "EarthquakeAdapter";

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

        TextView magnitude = listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentEarthquake.getMagnitude());



        String locationString = currentEarthquake.getLocation();
        TextView offsetView = listItemView.findViewById(R.id.offset);
        TextView locationView = listItemView.findViewById(R.id.location);

        if (locationString.contains(" of ")) {
            // split string into two parts
            String[] split = locationString.split("of");
            offsetView.setText(split[0] + " of ");
            locationView.setText(split[1]);

        } else {
            offsetView.setText("Near the ");
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


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("H:MM a");
        return timeFormat.format(dateObject);
    }
}
