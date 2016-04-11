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

    public final String magnitude;
    public final String location;
    public final String date;


    public Earthquake(double magnitude,
                      String location,
                      long rawTime
    ) {
        this.magnitude = String.format(Locale.getDefault(), "%.1f", magnitude);
        this.location = location;
        this.date = DateFormat.getDateTimeInstance().format(rawTime);
    }
}
