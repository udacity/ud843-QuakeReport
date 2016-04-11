# Region Preference

With the ground work for regional earthquake searches out of the way, time to add the region `ListPreference`! We've added some more strings and string arrays, but adding the preference to `settings_main.xml` is up to you this time. Then wire up the preference summary binding in `EarthquakePreferenceFragment`, and get the region of interest in `QueryUtils.getParamMap()`. Check out the TODOs to get started!
