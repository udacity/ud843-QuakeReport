package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Quinn on 9/10/16.
 */
public class EarthquakeListAdapter extends ArrayAdapter<EarthquakeEvent> {

    private final String TAG = "EarthquakeListAdapter";

    public EarthquakeListAdapter(Activity context, ArrayList<EarthquakeEvent> events) {
        super(context, 0, events);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_event_item, parent, false);
        }

        final EarthquakeEvent currentEvent = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_textview);
        magnitudeTextView.setText(String.valueOf(currentEvent.getmMagnitude()));

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_textview);
        locationTextView.setText(currentEvent.getmLocation());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_textview);
        dateTextView.setText(currentEvent.getmDate());


        return listItemView;
    }
}
