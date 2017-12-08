package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vijay on 12/5/2017.
 */

public class Earthquake_adapter extends ArrayAdapter<Earthquake> {



    public Earthquake_adapter(Activity context, ArrayList<Earthquake> earthquake) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_view, parent, false);
        }

        Earthquake earth=getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(earth.getMagnitude());

        TextView LocationTextView = (TextView) listItemView.findViewById(R.id.location);
        LocationTextView.setText(earth.getLocation());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.date);
        timeTextView.setText(earth.getTime());





        return listItemView;
    }
}
