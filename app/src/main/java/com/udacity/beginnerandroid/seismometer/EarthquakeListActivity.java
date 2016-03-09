package com.udacity.beginnerandroid.seismometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.udacity.beginnerandroid.seismometer.model.Earthquake;
import com.udacity.beginnerandroid.seismometer.model.GeoCoordinate;
import com.udacity.beginnerandroid.seismometer.settings.EarthquakeListSettingsActivity;
import com.udacity.beginnerandroid.seismometer.util.QueryUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Here's where it all begins: in the main activity.
 */
public class EarthquakeListActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeListActivity.class.getName();

    private EarthquakeAdapter mEarthquakeAdapter;

    private ConnectivityManager mConnectionManager;
    private HashMap<String, GeoCoordinate> mRegionsMap;
    private ProgressBar mProgressBar;
    private TextView mErrorTextView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_list_activity);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        mErrorTextView = (TextView) findViewById(R.id.error_text_view);

        mContext = this;

        mConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEarthquakeAdapter = new EarthquakeAdapter(this, inflater, new ArrayList<Earthquake>());

        ListView list = (ListView) findViewById(R.id.quake_list_view);
        list.setAdapter(mEarthquakeAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = mEarthquakeAdapter.getItem(position);
                Uri webpage = Uri.parse(earthquake.mUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        // TODO Add More Cities To Cover More Of The Globe, Preferably with Tectonic Activity
        mRegionsMap = new HashMap<>();
        mRegionsMap.put(
                getString(R.string.settings_region_san_francisco_key),
                new GeoCoordinate(37.7749, -122.4194));
        mRegionsMap.put(
                getString(R.string.settings_region_puerto_vallarta_key),
                new GeoCoordinate(20.6220, -105.2283));
        mRegionsMap.put(
                getString(R.string.settings_region_morocco_key),
                new GeoCoordinate(31.6333, -8.0000));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, EarthquakeListSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateEarthquakeData();
    }

    private void updateEarthquakeData() {
        NetworkInfo networkInfo = mConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(this);

            String region = sharedPrefs.getString(
                    getString(R.string.settings_region_key),
                    getString(R.string.settings_region_default));

            String orderBy = sharedPrefs.getString(
                    getString(R.string.settings_sort_by_key),
                    getString(R.string.settings_sort_by_magnitude_key));


            String minMagnitude = sharedPrefs.getString(
                    getString(R.string.settings_min_magnitude_key),
                    getString(R.string.settings_min_magnitude_default));

            String maxRadius = sharedPrefs.getString(
                    getString(R.string.settings_max_radius_key),
                    getString(R.string.settings_max_radius_default));


            URL url = buildURL(region, orderBy, minMagnitude, maxRadius);
            new FetchEarthquakeDataTask().execute(url);

        } else {
            mErrorTextView.setText(getString(R.string.error_no_connection));
        }

    }

    private URL buildURL(String region, String orderBy, String minMagnitude, String maxRadius) {
        Builder uriBuilder = Uri.parse(getString(R.string.base_url)).buildUpon()
                .appendQueryParameter(getString(R.string.format_param), getString(R.string.response_format))
                .appendQueryParameter(getString(R.string.limit_param), getString(R.string.limit))
                .appendQueryParameter(getString(R.string.event_type_param), getString(R.string.event_type))
                .appendQueryParameter(getString(R.string.order_by_param), orderBy)
                .appendQueryParameter(getString(R.string.min_magnitude_param), minMagnitude);

        if (!region.equals(getString(R.string.settings_region_default))) {
            String latitude = Double.toString(mRegionsMap.get(region).mLatitude);
            String longitude = Double.toString(mRegionsMap.get(region).mLongitude);

            uriBuilder.appendQueryParameter(getString(R.string.latitude_param), latitude)
                    .appendQueryParameter(getString(R.string.longitude_param), longitude)
                    .appendQueryParameter(getString(R.string.max_radius_param), maxRadius);
        }

        URL url = null;
        try {
            url = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL: " + e.getLocalizedMessage());
        }
        return url;
    }


    private class FetchEarthquakeDataTask extends AsyncTask<URL, Void, ArrayList<Earthquake>> {

        private static final long LOADING_INDICATOR_DELAY = 500L;
        final Handler handler = new Handler();
        final Runnable displayLoadingIndicator = new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        };

        @Override
        protected ArrayList<Earthquake> doInBackground(URL... urls) {
            String json = QueryUtils.getJSONFromWeb(urls[0]);
            return QueryUtils.extractFeatureArrayFromJson(json, mContext);
        }

        @Override
        protected void onPreExecute() {
            handler.postDelayed(displayLoadingIndicator, LOADING_INDICATOR_DELAY);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {
            if (result != null && !result.isEmpty()) {
                mEarthquakeAdapter.clear();
                mEarthquakeAdapter.addAll(result);
            } else {
                mErrorTextView.setText(getString(R.string.error_no_results));
            }
            handler.removeCallbacks(displayLoadingIndicator);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
