package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.beginnerandroid.seismometer.Model.Feature;
import com.udacity.beginnerandroid.seismometer.Model.GeoCoordinate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SEISMOMETER";

    private FeatureAdapter mFeatureAdapter;
    private static final int MAX_QUAKE_LIMIT = 20;

    private ArrayList<Feature> mFeatureList;

    private  ConnectivityManager mConnectionManager;

    private HashMap<String, GeoCoordinate> mRegionsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Feature Array
        mFeatureList = new ArrayList<Feature>();
        /* Initialize ArrayList
        for(int i = 0; i < MAX_QUAKE_LIMIT; i++) {
            mFeatureList.add(new Feature(0.0,"Default Place"));
        }*/

        // Fetch the {@link LayoutInflater} service so that new views can be created
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        ListView list = (ListView) findViewById(R.id.quake_list_view);
        mFeatureAdapter = new FeatureAdapter(this, inflater, mFeatureList);
        list.setAdapter(mFeatureAdapter);

        mConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize a few locations Users can choose from
        mRegionsMap = new HashMap<String, GeoCoordinate>();
        mRegionsMap.put("San Francisco", new GeoCoordinate(37.7749, -122.4194));
        mRegionsMap.put("Puerto Vallarta", new GeoCoordinate(20.6220, -105.2283));
        mRegionsMap.put("Morocco", new GeoCoordinate(31.6333, -8.0000));
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

    @Override
    protected void onStart() {
        super.onStart();
        updateFeatureInfo();
    }

    private void updateFeatureInfo() {
        NetworkInfo networkInfo = mConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String responseFormat = "geojson";
            String eventType = "earthquake";

            // Construct the URL for the USGS API query
            // Possible parameters are available at The USGS Earthquake API page, at
            // http://earthquake.usgs.gov/fdsnws/event/1/#parameters

            // Parameters Common To All Queries
            final String EARTHQUAKE_QUERY_BASE_URL =
                    "http://earthquake.usgs.gov/fdsnws/event/1/query?";
            final String FORMAT_PARAM = "format";
            final String LIMIT_PARAM = "limit";
            final String ORDER_BY_PARAM = "orderby";
            final String EVENT_TYPE_PARAM = "eventtype";
            final String MIN_MAGNITUDE_PARAM = "minmagnitude";

            // Parameters specific to World/Global Query
            final String START_TIME_PARAM = "starttime";
            final String END_TIME_PARAM = "endtime";

            // Parameters specific to Circular Search/Using a Radius in Kilometers
            // http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&latitude=35.6833&longitude=139.6833&maxradiuskm=80&limit=20&orderby=magnitude&eventtype=earthquake
            final String LATITUDE_PARAM = "latitude";
            final String LONGITUDE_PARAM = "longitude";
            final String MAX_RADIUS_PARAM = "maxradiuskm";

            // Extract Configuration From SharedPreferences
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(this);

            String regionPreference = sharedPrefs.getString(
                    getString(R.string.list_preference_region_key),
                    getString(R.string.settings_units_label_region_default));

            String orderByPreference = sharedPrefs.getString(
                    getString(R.string.list_preference_sort_by_key),
                    getString(R.string.settings_units_value_sort_by_mag_des));

            Uri builtUri;
            if (regionPreference.equals(getString(R.string.settings_units_label_region_default))) {

                // TODO - Need To Calculate Date in Proper Format Based On Current Day

                builtUri = Uri.parse(EARTHQUAKE_QUERY_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, responseFormat)
                        .appendQueryParameter(LIMIT_PARAM, "50")
                        .appendQueryParameter(EVENT_TYPE_PARAM, eventType)
                        .appendQueryParameter(ORDER_BY_PARAM, orderByPreference)
                        .appendQueryParameter(MIN_MAGNITUDE_PARAM, "2.0")
                        .appendQueryParameter(START_TIME_PARAM, "2016-01-21")
                        .appendQueryParameter(END_TIME_PARAM, "2016-01-28")
                        .build();
            } else {
                builtUri = Uri.parse(EARTHQUAKE_QUERY_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, responseFormat)
                        .appendQueryParameter(LIMIT_PARAM, "50")
                        .appendQueryParameter(EVENT_TYPE_PARAM, eventType)
                        .appendQueryParameter(ORDER_BY_PARAM, orderByPreference)
                        .appendQueryParameter(MIN_MAGNITUDE_PARAM, "2.0")
                        .appendQueryParameter(LATITUDE_PARAM,
                                Double.toString(mRegionsMap.get(regionPreference).getLatitude()))
                        .appendQueryParameter(LONGITUDE_PARAM,
                                Double.toString(mRegionsMap.get(regionPreference).getLongitude()))
                        .appendQueryParameter(MAX_RADIUS_PARAM, "1000")
                        .build();
            }

            Log.d(LOG_TAG,"URL BUILT is " + builtUri.toString());

            //String baseUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-21&endtime=2016-01-28&limit=20&orderby=magnitude&eventtype=earthquake";
            new FetchEarthquakeDataTask().execute(builtUri.toString());
        }
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
                mFeatureList = ParsingUtils.extractFeatureArrayFromJson(result);
            }

            mFeatureAdapter.clear();
            for(int i = 0; i < mFeatureList.size(); i++) {
                mFeatureAdapter.add(mFeatureList.get(i));
            }

            // Method Chaining Approach to quick Toast to indicate updated data
            Toast.makeText(getApplicationContext(),
                    "Data Update Complete", Toast.LENGTH_SHORT).show();
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
