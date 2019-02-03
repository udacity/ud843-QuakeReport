package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        final Earthquake earthquake = getItem(position);

        String location = earthquake.getLocation();
        int index = location.indexOf(" of ");
        String _loc;
        String _place;
        if(index!=-1) {
             _loc= location.substring(0, index+4);
             _place= location.substring(index + 4, location.length());
        }
        else{
            _loc = "Near the";
            _place = location;
        }

        String magnitude = earthquake.getMag();
        float _mag = Float.parseFloat(magnitude);

        TextView mag = (TextView) listItemView.findViewById(R.id.mag);
        mag.setText(String.valueOf(_mag));
        // setting color acc to the magnitude

        // this will return the required color
        int magnitudeColorResourceId = set_color(_mag);
        // a gradient drawable is the one that can change its color
        GradientDrawable circle = (GradientDrawable) mag.getBackground();
        circle.setColor(magnitudeColorResourceId);

        TextView loc = (TextView) listItemView.findViewById(R.id.loc);
        loc.setText(_loc);
        TextView place = (TextView) listItemView.findViewById(R.id.place);
        place.setText(_place);
        TextView date  = (TextView) listItemView.findViewById(R.id.date);
        date.setText(earthquake.getDate());
        TextView time  = (TextView) listItemView.findViewById(R.id.time);
        time.setText(earthquake.getTime());


        // opeing the details link on clicking a certain item
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent earthquakeIntent = new Intent(Intent.ACTION_VIEW);
                earthquakeIntent.setData(Uri.parse(earthquake.getUrl()));
                getContext().startActivity(earthquakeIntent);
            }
        });

        return listItemView;

    }

int set_color(float _mag){
    int magnitudeColorResourceId;

    switch((int)_mag){
        case 0:
        case 1:
            magnitudeColorResourceId = R.color.magnitude1;
            break;
        case 2:
            magnitudeColorResourceId = R.color.magnitude2;
            break;
        case 3:
            magnitudeColorResourceId = R.color.magnitude3;
            break;
        case 4:
            magnitudeColorResourceId = R.color.magnitude4;
            break;
        case 5:
            magnitudeColorResourceId = R.color.magnitude5;
            break;
        case 6:
            magnitudeColorResourceId = R.color.magnitude6;
            break;
        case 7:
            magnitudeColorResourceId = R.color.magnitude7;
            break;
        case 8:
            magnitudeColorResourceId = R.color.magnitude8;
            break;
        case 9:
            magnitudeColorResourceId = R.color.magnitude9;
            break;
        default:
            magnitudeColorResourceId = R.color.magnitude10plus;
            break;
    }

    // this will convert the color id into a valid color
    return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
}
}
