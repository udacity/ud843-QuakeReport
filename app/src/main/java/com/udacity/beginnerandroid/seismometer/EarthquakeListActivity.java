package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.beginnerandroid.seismometer.Model.Earthquake;
import com.udacity.beginnerandroid.seismometer.Model.GeoCoordinate;
import com.udacity.beginnerandroid.seismometer.Settings.SettingsActivity;
import com.udacity.beginnerandroid.seismometer.Util.ParsingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


/**
 * Here's where it all begins: in the main activity.
 */
public class EarthquakeListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Earthquakes";
    private static final int MAX_QUAKE_LIMIT = 20;
    private EarthquakeAdapter mEarthquakeAdapter;
    private ArrayList<Earthquake> mEarthquakeList;

    private ConnectivityManager mConnectionManager;

    private HashMap<String, GeoCoordinate> mRegionsMap;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_list_activity);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        // initialize Earthquake Array
        mEarthquakeList = new ArrayList<>();

        // Fetch the {@link LayoutInflater} service so that new views can be created
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        ListView list = (ListView) findViewById(R.id.quake_list_view);
        mEarthquakeAdapter = new EarthquakeAdapter(this, inflater, mEarthquakeList);
        list.setAdapter(mEarthquakeAdapter);

        // TODO - Explain To Students Why We need to Use AdapterView.OnItemClickListener To Handle Input
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = mEarthquakeAdapter.getItem(position);
                Uri webpage = Uri.parse(earthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        mConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize a Few Locations That Users Can Choose From
        // TODO Add More Cities To Cover More Of The Globe, Preferably with Tectonic Activity
        mRegionsMap = new HashMap<>();
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


            // TODO: Add all these to strings.xml
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

            // TODO: Add these strings to strings.xml

            String minMagnitudePreference = sharedPrefs.getString("min_magnitude", "0.0");

            String maxRadiusPreference = sharedPrefs.getString("max_radius", "100");

            Uri builtUri;

            if (regionPreference.equals(getString(R.string.settings_units_label_region_default))) {

                //TODO Remove/Hide Radius Preference When World Is Selected

                // Extract The Current Date From The System Time &
                // And Use As The endDate parameter to the API
                Date today = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String endDate = sdf.format(today);

                builtUri = Uri.parse(EARTHQUAKE_QUERY_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, responseFormat)
                        .appendQueryParameter(LIMIT_PARAM, "50")
                        .appendQueryParameter(EVENT_TYPE_PARAM, eventType)
                        .appendQueryParameter(ORDER_BY_PARAM, orderByPreference)
                        .appendQueryParameter(MIN_MAGNITUDE_PARAM, minMagnitudePreference)
                        .appendQueryParameter(END_TIME_PARAM, endDate)
                        .build();
            } else {
                builtUri = Uri.parse(EARTHQUAKE_QUERY_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, responseFormat)
                        .appendQueryParameter(LIMIT_PARAM, "50")
                        .appendQueryParameter(EVENT_TYPE_PARAM, eventType)
                        .appendQueryParameter(ORDER_BY_PARAM, orderByPreference)
                        .appendQueryParameter(MIN_MAGNITUDE_PARAM, minMagnitudePreference)
                        .appendQueryParameter(LATITUDE_PARAM,
                                Double.toString(mRegionsMap.get(regionPreference).getLatitude()))
                        .appendQueryParameter(LONGITUDE_PARAM,
                                Double.toString(mRegionsMap.get(regionPreference).getLongitude()))
                        .appendQueryParameter(MAX_RADIUS_PARAM, maxRadiusPreference)
                        .build();
            }


            try {
                URL url = new URL(builtUri.toString());
                new FetchEarthquakeDataTask().execute(url);
            } catch (MalformedURLException e) {

                Log.e(LOG_TAG, "Problem building the URL: " + e.getLocalizedMessage());
            }

            // For Debug
            // Log.d(LOG_TAG,"URL BUILT is " + builtUri.toString());

            // TODO: Start Teaching Students Using A Base URL String
            //String baseUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-21&endtime=2016-01-28&limit=20&orderby=magnitude&eventtype=earthquake";

        }
    }

    protected String getJSONFromWeb(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader;

        String earthquakeDataJSON;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Read the input Stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return "";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
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
    }

    // TODO: Extract into
    private class FetchEarthquakeDataTask extends AsyncTask<URL, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(URL... urls) {

            String json = getJSONFromWeb(urls[0]);
            return ParsingUtils.extractFeatureArrayFromJson(json);


        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {
            if (result != null) {
                mEarthquakeList = result;


                mEarthquakeAdapter.clear();
                for (int i = 0; i < mEarthquakeList.size(); i++) {
                    mEarthquakeAdapter.add(mEarthquakeList.get(i));
                }

            } else {
                Toast.makeText(getApplicationContext(),
                        "No Earthquakes Found", Toast.LENGTH_SHORT).show();
            }

            mProgressBar.setVisibility(View.INVISIBLE);
        }



    }
}



