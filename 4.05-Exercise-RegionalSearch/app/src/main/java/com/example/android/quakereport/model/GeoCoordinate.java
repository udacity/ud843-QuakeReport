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


import java.util.Locale;

public class GeoCoordinate {
    public final double latitude;
    public final double longitude;

    public GeoCoordinate(double latitude, double longitude) {

        if (latitude >= -90 && latitude <= 90) {
            this.latitude = latitude;
        } else {
            throw new IllegalArgumentException(String.format(Locale.getDefault(),
                    "Latitude %.1f must be between -90 and 90.", latitude));
        }

        // TODO: Make sure that -180 <= longitude <= 180, and throw IllegalArgumentException if not
            this.longitude = longitude;

    }
}



