package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class earthquake_arrayAdpater extends ArrayAdapter<Earthquake>{
    public earthquake_arrayAdpater(Activity context, ArrayList<Earthquake>quakelist) {
        super(context,0,quakelist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list, parent, false);
        }

        Earthquake earthquake = getItem(position);

        TextView mag = (TextView) listItemView.findViewById(R.id.mag);
        mag.setText(earthquake.getMag());
        TextView loc = (TextView) listItemView.findViewById(R.id.loc);
        loc.setText(earthquake.getLocation());
        TextView date  = (TextView) listItemView.findViewById(R.id.date);
        date.setText(earthquake.getDate());
        TextView time  = (TextView) listItemView.findViewById(R.id.time);
        time.setText(earthquake.getTime());

        return listItemView;

    }
}
