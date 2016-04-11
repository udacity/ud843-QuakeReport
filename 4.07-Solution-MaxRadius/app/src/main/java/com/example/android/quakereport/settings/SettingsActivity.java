/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.quakereport.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.quakereport.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements OnPreferenceChangeListener {

        private Preference mRegionPreference;
        private Preference mMaxRadiusPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);


            // TODO: Find and save mRegionPreference and mMaxRadiusPreference
            mRegionPreference = findPreference(getString(R.string.settings_region_key));
            mMaxRadiusPreference = findPreference(getString(R.string.settings_max_radius_key));

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));

            bindPreferenceSummaryToValue(mRegionPreference);

            // TODO: Bind the max radius preference
            bindPreferenceSummaryToValue(mMaxRadiusPreference);
            
            bindPreferenceSummaryToValue(minMagnitude);
            bindPreferenceSummaryToValue(orderBy);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }

            // TODO: Check if the preference is mRegionPreference
            if (preference.equals(mRegionPreference)) {

                // TODO: Check if the new stringValue is R.string.settings_region_world_key
                if (stringValue.equals(getString(R.string.settings_region_world_key))) {

                    // TODO: If so setEnabled(false) on mMaxRadiusPreference
                    mMaxRadiusPreference.setEnabled(false);
                } else {

                    // TODO: If not, enable mMaxRadiusPreference
                    mMaxRadiusPreference.setEnabled(true);
                }
            }

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
