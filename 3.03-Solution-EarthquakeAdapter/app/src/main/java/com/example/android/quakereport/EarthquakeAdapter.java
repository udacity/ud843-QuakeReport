package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.model.Earthquake;

import java.util.Locale;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private final LayoutInflater mInflater;

    public EarthquakeAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // TODO: If the view we were passed for reuse is null, inflate a new R.layout.earthquake_list_item
        // Pass in parent for the root ViewGroup, and don't attach the new view
        if (view == null) {
            view = mInflater.inflate(R.layout.earthquake_list_item, parent, false);
        }

        // TODO: Use view.getTag() to get the view's EarthquakeViewHolder
        EarthquakeViewHolder viewHolder = (EarthquakeViewHolder) view.getTag();

        // TODO: If the view holder is null, create a new EarthquakeViewHolder for this view
        // And attach that view holder to the view using setTag()
        if (viewHolder == null) {
            viewHolder = new EarthquakeViewHolder(view);
            view.setTag(viewHolder);
        }

        // TODO: Use getItem(position) to get the Earthquake for this list item
        Earthquake earthquake = getItem(position);

        // TODO: Populate the magnitude, location, and date TextFields from the view holder
        viewHolder.magnitude.setText(earthquake.magnitude);
        viewHolder.location.setText(earthquake.location);
        viewHolder.date.setText(earthquake.date);

        // TODO: Repeat the logic of the previous exercise to populate the locationOffset
        // If the earthquake has a location offset, combine it with R.string.location_of
        if (!earthquake.locationOffset.isEmpty()) {
            String locationOffsetString = String.format(Locale.getDefault(),
                    getContext().getString(R.string.location_of),
                    earthquake.locationOffset
            );
            viewHolder.locationOffset.setText(locationOffsetString);
        } else {
            // If the earthquake doesn't have a location offset,
            // set the locationOffset TextView contents to R.string.location_near_the
            viewHolder.locationOffset.setText(getContext().getString(R.string.location_near_the));
        }
        return view;
    }


    class EarthquakeViewHolder {

        // TODO: Add a public final TextView for the magnitude, locationOffset, location, and date
        public final TextView magnitude;
        public final TextView locationOffset;
        public final TextView location;
        public final TextView date;

        public EarthquakeViewHolder(View view) {

            // TODO: Use view.vindViewByID() to populate the TextViews above
            magnitude = (TextView) view.findViewById(R.id.magnitude);
            locationOffset = (TextView) view.findViewById(R.id.location_offset);
            location = (TextView) view.findViewById(R.id.location);
            date = (TextView) view.findViewById(R.id.date);

        }
    }
}
