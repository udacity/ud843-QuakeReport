package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.udacity.beginnerandroid.seismometer.Model.Feature;

import java.util.ArrayList;

/**
 * Created by chrislei on 1/21/16.
 */
public class FeatureAdapter extends ArrayAdapter<Feature> {

    private Context mContext;
    private LayoutInflater mInflater;

    public FeatureAdapter(Context context, LayoutInflater inflater, ArrayList<Feature> features) {
        super(context, R.layout.list_item_feature, features);
        mContext = context;
        mInflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = mInflater.inflate(R.layout.list_item_feature, parent, false);
        }

        // Try to get view cache or create a new one if needed
        ViewCache viewCache = (ViewCache) result.getTag();
        if (viewCache == null) {
            viewCache = new ViewCache(result);
            result.setTag(viewCache);
        }

        // Fetch item
        final Feature earthquakeFeature = getItem(position);

        // Bind the data
        viewCache.magnitudeView.setText(String.format("%.4f", earthquakeFeature.getMagnitude()));
        viewCache.placeView.setText(earthquakeFeature.getPlace());

        viewCache.magnitudeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        viewCache.placeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
        return result;

    }

}
