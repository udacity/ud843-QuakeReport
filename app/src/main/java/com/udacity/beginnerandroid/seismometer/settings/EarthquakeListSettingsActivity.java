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

            addPreferencesFromResource(R.xml.settings_main);

            mRegionPreference = findPreference(getString(R.string.settings_region_key));
            mMaxRadiusPreference = findPreference(getString(R.string.settings_max_radius_key));


            bindPreferenceSummaryToValue(mRegionPreference);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_sort_by_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.settings_min_magnitude_key)));
            bindPreferenceSummaryToValue(mMaxRadiusPreference);

        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence label = listPreference.getEntries()[prefIndex];
                    preference.setSummary(label);
                }
            } else {
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
