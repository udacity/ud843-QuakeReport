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

package com.example.android.quakereport.model;

import java.text.DateFormat;
import java.util.Locale;

public class Earthquake {

    private static final String LOCATION_SEPARATOR = " of ";

    public final String magnitude;
    public final String location;
    public final String locationOffset;
    public final String date;

    public Earthquake(double magnitude,
                      String rawLocation,
                      long rawTime
    ) {
        this.magnitude = String.format(Locale.getDefault(), "%.1f", magnitude);

        // TODO: Check if the rawLocation contains the LOCATION_SEPARATOR
        if (rawLocation.contains(LOCATION_SEPARATOR)) {

            // TODO: If so, use rawLocation.split() to chop the rawLocation into the locationOffset and location
            String[] splitLocation = rawLocation.split(LOCATION_SEPARATOR);
            this.locationOffset = splitLocation[0];
            this.location = splitLocation[1];

        } else {

            // TODO: If not, set location to rawLocation, and set locationOffset to the empty string
            this.locationOffset = "";
            this.location = rawLocation;

        }

        // TODO: Format the date using DateFormat.getDateInstance()
        String date = DateFormat.getDateInstance().format(rawTime);

        // TODO: Format the time using DateFormat.getTimeInstance()
        String time = DateFormat.getTimeInstance().format(rawTime);

        // TODO: Combine date + "\n" + time and save it as this.date
        this.date = date + "\n" + time;
    }
}
