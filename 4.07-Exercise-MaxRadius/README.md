# Max Radius

TODO: Start here!

One more feature to add! Let's add a user preference for the radius of the area that the user wants to query for earthquakes. This is just an `EditTextPreference` like the min magnitude, so most of the work of setting up this preference is up to you.

The only wrinkle with this setting is that it's only relevant when the region setting isn't "world". That means we should add some more logic to `EarthquakePreferenceFragment.onPreferenceChanged()` to enable and disable the max radius preference as appropriate.

Check out the TODOs to get started! First add the preference in `settings_main.xml`, then wire it up in `QueryUtils.getParamMap()`, and finally add the summary and enable/disable logic in `EarthquakePreferenceFragment`!


You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/4.07-Exercise-MaxRadius.zip), and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/4.07-Solution-MaxRadius.zip). Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/4.07-Exercise-MaxRadius...4.07-Solution-MaxRadius).

