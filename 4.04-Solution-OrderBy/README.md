# Order By

TODO: Start here!

Let's add another preference to allow the user to specify whether they'd like their list of earthquakes sorted with the mot recent earthquake at the top, or the earthquake with the largest magnitude at the top. The USGS API can take care of the actual ordering for us, but the `EditTextPreference` we used for the min magnitude isn't a great match for this sort of setting. Instead we'll use `ListPreference`. A list preference, as it sounds, allows the user to select one of a list of options. We'll use a string array resource file to keep track of what options are available.

The only wrinkle comes in the fact that we would like to make these option names translatable, but then what happens if the user does something like change the language setting on their device? How do we know what language to interpret the option they've selected? The solution is to maintain two arrays, one of the translatable labels for the available options, and one for untranslatable keys that go along with the labels.

We've added labels and key strings to `strings.xml`, and arrays of labels and keys to `arrays.xml`. Finally, we've added a `ListPreference` to `settings_main.xml`. Find our additions by following the TODOs.

# The Exercise

It's up to you to wire up the order-by preference in the `QueryUtils.getParamsMap()` method, then update the `EarthquakePreferenceFragment.onPreferenceChange()` method to properly update the order-by preference's summary. Check out the TODOs to get started, and open the solution project if you get stuck!
