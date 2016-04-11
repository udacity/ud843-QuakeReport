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

    // TODO: Note that we've declared that EarthquakePreferenceFragment implements OnPreferenceChangeListener
    public static class EarthquakePreferenceFragment extends PreferenceFragment implements OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // TODO: Get a reference to our min magnitude preference
            // Use findPreference() and R.string.settings_min_magnitude_key
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));

            // TODO: Call bindPreferenceSummaryToValue() with the reference to the min magnitude preference
            bindPreferenceSummaryToValue(minMagnitude);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            // TODO: Get the string value of the preference
            // Use value.toString()
            String stringValue = value.toString();

            // TODO: Set the preference's summary to the string value
            preference.setSummary(stringValue);

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {

            // TODO: Set SettingsFragment as a change listener
            // Use preference.setOnPreferenceChangeListener()
            preference.setOnPreferenceChangeListener(this);

            // TODO: Get the default shared preferences
            // Use PreferenceManager.getDefaultSharedPreferences()
            // You can get the context using preference.getContext()
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            // TODO: Get the string value of the preference
            // Use getString() on SharedPreferences, and preference.getKey()
            // Just pass the empty string as the default
            String preferenceString = preferences.getString(preference.getKey(), "");

            // TODO: Call onPreferenceChange()
            // Passing in the preference, and the string value
            onPreferenceChange(preference, preferenceString);
        }
    }
}
