package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.text.TextUtils.substring;

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

        /*
         The following code the part of setting the magnitude
         */
        //This is for setting the magnitudes
        TextView magnitudeNumber = (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat formatter = new DecimalFormat("0.0");            //formatting to get perfect Format
        String output = formatter.format(current_earthquake_data.getMagnitude());
        magnitudeNumber.setText(output);


        /*
          This does the part of the Setting of the String places values
         */
        //This is for setting the data for the Place
        String placeString = current_earthquake_data.getPlace();
        ArrayList<String> placeData = new ArrayList<String>(getThePlace(placeString));

        TextView placeView = (TextView) listItemView.findViewById(R.id.location);
        placeView.setText(placeData.get(1));

        TextView placeLandmarkData =(TextView) listItemView.findViewById(R.id.landmark);
        placeLandmarkData.setText(placeData.get(0));

        /*
        This is for the setting of the Date
         */

        //Now the part of Date conversion Comes
        Date date = new Date(current_earthquake_data.getDate());

        TextView dateData = (TextView)listItemView.findViewById(R.id.date);
        //Format the data
        String formatDate =formatDate(date);
        dateData.setText(formatDate);

        TextView timeData =(TextView) listItemView.findViewById(R.id.time);
        String formatTime = formatTime(date);
        timeData.setText(formatTime);

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return  dateFormat.format(dateObject);
    }

    private String formatTime (Date dateObject){
        SimpleDateFormat dateFormat  = new SimpleDateFormat("h:mm a");
        return dateFormat.format(dateObject);
    }

    private ArrayList<String> getThePlace(String placeString){          //This is for the seperating the string for the place data
        if(placeString.contains("of")){
            int index = placeString.indexOf("of");
            String placeToBeDisplayed = placeString.substring(0,index+2);
            String landMarkToBeDisplayed = placeString.substring(index+2,placeString.length());
            ArrayList<String> place = new ArrayList<String>();
            place.add(placeToBeDisplayed);
            place.add(landMarkToBeDisplayed);
            return place;
        }
        else {
            ArrayList<String> place = new ArrayList<String>();
            place.add(" ");
            place.add(placeString);
            return place;
        }
    }
}
