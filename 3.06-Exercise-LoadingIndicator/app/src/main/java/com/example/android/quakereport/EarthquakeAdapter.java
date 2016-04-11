package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.Utils;

import java.util.Locale;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private final LayoutInflater mInflater;

    public EarthquakeAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.earthquake_list_item, parent, false);
        }

        EarthquakeViewHolder viewHolder = (EarthquakeViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new EarthquakeViewHolder(view);
            view.setTag(viewHolder);
        }

        Earthquake earthquake = getItem(position);
        viewHolder.magnitude.setText(earthquake.magnitude);
        viewHolder.location.setText(earthquake.location);
        viewHolder.date.setText(earthquake.date);

        if (!earthquake.locationOffset.isEmpty()) {
            String locationOffsetString = String.format(Locale.getDefault(),
                    getContext().getString(R.string.location_of),
                    earthquake.locationOffset
            );
            viewHolder.locationOffset.setText(locationOffsetString);
        } else {
            viewHolder.locationOffset.setText(getContext().getString(R.string.location_near_the));
        }

        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.magnitude.getBackground();
        double magnitude  = Double.parseDouble(earthquake.magnitude);
        magnitudeCircle.setColor(Utils.getMagnitudeColor(getContext(), magnitude));

        return view;
    }


    class EarthquakeViewHolder {

        public final TextView magnitude;
        public final TextView locationOffset;
        public final TextView location;
        public final TextView date;

        public EarthquakeViewHolder(View view) {

            magnitude = (TextView) view.findViewById(R.id.magnitude);
            locationOffset = (TextView) view.findViewById(R.id.location_offset);
            location = (TextView) view.findViewById(R.id.location);
            date = (TextView) view.findViewById(R.id.date);

        }
    }
}
