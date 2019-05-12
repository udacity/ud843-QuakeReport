package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

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

        TextView location = listItemView.findViewById(R.id.location);
        location.setText(currentEarthquake.getLocation());

        TextView date = listItemView.findViewById(R.id.date);
        date.setText(currentEarthquake.getDate());

        return listItemView;
    }
}
