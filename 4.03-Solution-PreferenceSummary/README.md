# Preference Summary

TODO: Start here!

It'd be nice to see the current value of each preference at a glance in `SettingsActivity`, and actually `PreferenceFragment` has a solution ready to go. `Preference` has a `summary` field that's displayed below the preference's name. So all we need to do is set our min magnitude preference's `summary` when `EarthquakePreferenceFragment` is created, and any time the preference changes.

To do that, we need to tell our `Preference` that `EarthquakePreferenceFragment` would like to be notified any time the `Preference` changes. We can do that by having `EarthquakePreferenceFragment` implement the `OnPreferenceChangeListener` interface, overriding the `onPreferenceChange()` method.

So here's the plan of attack:

1. Set up `onPreferenceChange()` to update the preference's summary.
2. Tell the min magnitude preference that `EarthquakePreferenceFragment` is a `OnPreferenceChangeListener`.
3. Manually call `onPreferenceChange()` to set the preference's initial summary.

# The Exercise

This one is all in `SettingsActivity`. Check out the TODOs to get started!


You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/4.03-Exercise-PreferenceSummary.zip), and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/4.03-Solution-PreferenceSummary.zip). Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/4.03-Exercise-PreferenceSummary...4.03-Solution-PreferenceSummary).

