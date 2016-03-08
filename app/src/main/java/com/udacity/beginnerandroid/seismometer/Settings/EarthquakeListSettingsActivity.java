package com.udacity.beginnerandroid.seismometer.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.beginnerandroid.seismometer.R;

public class EarthquakeListSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    // TODO: Explain to students why this declaration is static
    public static class SettingsFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        private Preference mRegionPreference;
        private Preference mMaxRadiusPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the settings_main from an XML resource
            addPreferencesFromResource(R.xml.settings_main);

            mRegionPreference = findPreference(getString(R.string.settings_region_key));
            mMaxRadiusPreference = findPreference(getString(R.string.settings_max_radius_key));


            bindPreferenceSummaryToValue(mRegionPreference);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_sort_by_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_min_magnitude_key)));
            bindPreferenceSummaryToValue(mMaxRadiusPreference);

        }

        /**
         * Attaches a listener so the summary is always updated with the preference value. Also
         * fires the listener once, to initialize the summary (so it shows up before the value is
         * changed.)
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(this);

            // Trigger the listener immediately with the preference's
            // current value.
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence label = listPreference.getEntries()[prefIndex];
                    preference.setSummary(label);
                }
            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }

            if (preference.equals(mRegionPreference)) {
                if (stringValue.equals(getString(R.string.settings_region_world_key))) {
                    mMaxRadiusPreference.setEnabled(false);
                } else {
                    mMaxRadiusPreference.setEnabled(true);
                }
            }

            return true;
        }
    }
}
