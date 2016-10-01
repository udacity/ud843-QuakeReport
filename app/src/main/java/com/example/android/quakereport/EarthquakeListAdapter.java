package com.example.android.quakereport;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Quinn on 9/10/16.
 */
public class EarthquakeListAdapter extends ArrayAdapter<EarthquakeEvent> {

    private final String TAG = "EarthquakeListAdapter";
    private final String LOCATION_SEPERATOR = " of ";
    // formatter used to set precision on event magnitude
    private DecimalFormat formatter = new DecimalFormat("0.0");

    /**
     * @param context
     * @param events
     */
    public EarthquakeListAdapter(EarthquakeActivity context, List<EarthquakeEvent> events) {
        super(context, 0, events);
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_event_item, parent, false);
        }

        // cursor to get next EarthquakeEvent to be displayed in the list
        final EarthquakeEvent currentEvent = getItem(position);

        // logic to split location string so that proximity and landmark are on separate lines
        String locationString = currentEvent.getmLocation();
        String placeString = "";
        String proximityString = "";
        int stringLength = locationString.length();
        if (locationString.contains(LOCATION_SEPERATOR)) {
            // be sure to get the correct sequence, "of", by including spaces before and after
            int index = locationString.indexOf(LOCATION_SEPERATOR);
            // check to avoid out of bounds exception
            if (locationString.length() >= index) {
                // end string after " of" with only a space before
                proximityString = locationString.substring(0, index + 3);
                // begin new string after " of " with a space before and after
                placeString = (locationString.substring(index + 4));
            }
        } else {
            placeString = locationString;
        }

        // magnitudeTextView displays the magnitude in a decimal format rounded to tenths
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_textview);
        magnitudeTextView.setText(String.valueOf(formatter.format(currentEvent.getmMagnitude())));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magColor = getMagColor(currentEvent.getmMagnitude());
        magnitudeCircle.setColor(magColor);

        // locationTextView displays the distance and direction from the placeTextView
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_textview);
        if (!proximityString.equals("")) {
            // locationTextView contains the distance and direction from the city landmark
            locationTextView.setText(proximityString);
        } else {
            // make invisible if there is no data to display
            locationTextView.setVisibility(View.INVISIBLE);
        }

        // placeTextView contains the city name or landmark that describes the quake's location
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_textview);
        // TODO: split locationTextView string and fill this view with Location Name
        placeTextView.setText(placeString);

        // display date in "MMM DD, yyyy" (formatted in QueryUtils.java)
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_textview);
        dateTextView.setText(currentEvent.getmDate());

        // display time in "hh:mma" (formatted in QueryUtils.java)
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_textView);
        timeTextView.setText(currentEvent.getmTime());

        return listItemView;
    }

    /**
     * @param mMagnitude
     * @return Helper function to set the color of the backround of the magnitude textview
     * Takes a double value for the magnitude and returns an int that references the color value
     * <p/>
     * Course documentation recommends rounding and using a switch-case statement instead.
     * Does that algorithm provide any benefit to the user or device resources?
     */
    public int getMagColor(double mMagnitude) {
        int magColor = 0;
        if (mMagnitude >= 0 && mMagnitude < 2) {
            magColor = R.color.magnitude1;
        } else if (mMagnitude >= 2 && mMagnitude < 3) {
            magColor = R.color.magnitude2;
        } else if (mMagnitude >= 3 && mMagnitude < 4) {
            magColor = R.color.magnitude3;
        } else if (mMagnitude >= 4 && mMagnitude < 5) {
            magColor = R.color.magnitude4;
        } else if (mMagnitude >= 5 && mMagnitude < 6) {
            magColor = R.color.magnitude5;
        } else if (mMagnitude >= 6 && mMagnitude < 7) {
            magColor = R.color.magnitude6;
        } else if (mMagnitude >= 7 && mMagnitude < 8) {
            magColor = R.color.magnitude7;
        } else if (mMagnitude >= 8 && mMagnitude < 9) {
            magColor = R.color.magnitude8;
        } else if (mMagnitude >= 9 && mMagnitude < 10) {
            magColor = R.color.magnitude9;
        } else if (mMagnitude >= 10) {
            magColor = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magColor);
    }
}
