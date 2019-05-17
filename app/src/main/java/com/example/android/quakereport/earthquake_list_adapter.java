package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class earthquake_list_adapter extends ArrayAdapter<earthquake_data_list> {

    private static final String LOG_TAG = earthquake_list_adapter.class.getName(); //This is for the log during debugging

    public earthquake_list_adapter(Activity context, ArrayList<earthquake_data_list> earthquake_data){
        super(context,0, earthquake_data);     //Initialisation
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
    }


    @Override
    public View getView(int position, View contentView, ViewGroup parent){
       //Check if existing view is being reused, otherwise inflate the view
        View listItemView = contentView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }

        earthquake_data_list current_earthquake_data = getItem(position);

        TextView magnitudeNumber = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeNumber.setText(current_earthquake_data.getMagnitude());

        TextView placeData = (TextView) listItemView.findViewById(R.id.location);
        placeData.setText((current_earthquake_data.getPlace()));

        TextView dateData = (TextView)listItemView.findViewById(R.id.date);
        dateData.setText(current_earthquake_data.getDate());

        return listItemView;
    }




}
