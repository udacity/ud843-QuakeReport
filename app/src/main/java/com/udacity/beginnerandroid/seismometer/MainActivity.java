package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.udacity.beginnerandroid.seismometer.Model.Feature;
import com.udacity.beginnerandroid.seismometer.Settings.SettingsActivity;
import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SEISMOMETER";

    private FeatureAdapter mFeatureAdapter;
    private static final int MAX_QUAKE_LIMIT = 20;

    private ArrayList<Feature> mFeatureList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Feature Array
        mFeatureList = new ArrayList<Feature>();
        for(int i = 0; i < MAX_QUAKE_LIMIT; i++) {
            mFeatureList.add(new Feature(0.0,"Default Place"));
        }

        // Fetch the {@link LayoutInflater} service so that new views can be created
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        ListView list = (ListView) findViewById(R.id.quake_list_view);
        mFeatureAdapter = new FeatureAdapter(this, inflater, mFeatureList);
        list.setAdapter(mFeatureAdapter);

        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String baseUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-13&endtime=2016-01-20&limit=20&orderby=magnitude";
            new FetchEarthquakeDataTask().execute(baseUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            if(result != null) {
                mFeatureList = ParsingUtils.extractFeatureArrayFromJson(result, MAX_QUAKE_LIMIT);
            }

            mFeatureAdapter.clear();
            for(int i = 0; i < MAX_QUAKE_LIMIT; i++) {
                mFeatureAdapter.add(mFeatureList.get(i));
            }
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
