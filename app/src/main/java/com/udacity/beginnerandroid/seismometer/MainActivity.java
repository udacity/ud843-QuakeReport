package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.beginnerandroid.seismometer.Model.Feature;
import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SEISMOMETER";
    private TextView mMagnitudeView;
    private TextView mPlaceView;
    private Button mRunQueryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMagnitudeView = (TextView) findViewById(R.id.actualMagnitude);
        mPlaceView = (TextView) findViewById(R.id.actualDescription);

        mRunQueryButton = (Button) findViewById(R.id.runQuery);
        mRunQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectionManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String baseUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-19&endtime=2016-01-20&limit=10&orderby=magnitude";
                    new FetchEarthquakeDataTask().execute(baseUrl);
                }
            }
        });
    }

    private class FetchEarthquakeDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return getJSONFromWeb(new URL(urls[0]));
            } catch (IOException e) {
                return "Unable to retrive response JSON from USGS. URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Feature earthquake = ParsingUtils.extractFeatureFromJson(result);
            mMagnitudeView.setText(String.format("%.4f",earthquake.getMagnitude()));
            mPlaceView.setText(earthquake.getPlace());
        }

        protected String getJSONFromWeb(URL url) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String earthquakeDataJSON = "";

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input Stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return "";
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return "";
                }

                earthquakeDataJSON = buffer.toString();
                return earthquakeDataJSON;

            } catch (IOException e) {
                //  Log exception here
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return "";
        } // END getJSONFromWeb
    }
}
