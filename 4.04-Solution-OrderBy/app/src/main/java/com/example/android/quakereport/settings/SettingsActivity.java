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

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            // TODO: Find the order by preference and call bindPreferenceSummaryToValue()
            // The key is R.string.settings_order_by_key
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            // TODO: Check if preference is instanceof ListPreference
            if (preference instanceof ListPreference) {

                // TODO: If so, cast preference to a ListPreference
                // ListPreference listPreference = (ListPreference) preference
                ListPreference listPreference = (ListPreference) preference;

                // TODO: Use findIndexOfValue() to find the index of stringValue
                int prefIndex = listPreference.findIndexOfValue(stringValue);

                // TODO: Check if the the index was greater than or equal to 0
                // Making sure the value was actually found
                if (prefIndex >= 0) {

                    // TODO: If so, use getEntries() to get the labels
                    CharSequence[] labels = listPreference.getEntries();

                    // TODO: Set the preference's summary to the label at the index found
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                // TODO: If preference is not an instance of ListPreference, set its summary to stringValue
                preference.setSummary(stringValue);
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
