package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.quakereport.model.Earthquake;


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

        // TODO: Use view.getTag() to get the view's EarthquakeViewHolder

        // TODO: If the view holder is null, create a new EarthquakeViewHolder for this view
        // And attach that view holder to the view using setTag()

        // TODO: Use getItem(position) to get the Earthquake for this list item

        // TODO: Populate the magnitude, location, and date TextFields from the view holder

        // TODO: Repeat the logic of the previous exercise to populate the locationOffset
        // If the earthquake has a location offset, combine it with R.string.location_of
            // If the earthquake doesn't have a location offset,
            // set the locationOffset TextView contents to R.string.location_near_the

        return view;
    }


    class EarthquakeViewHolder {

        // TODO: Add a public final TextView for the magnitude, locationOffset, location, and date

        public EarthquakeViewHolder(View view) {

            // TODO: Use view.vindViewByID() to populate the TextViews above

        }
    }
}
