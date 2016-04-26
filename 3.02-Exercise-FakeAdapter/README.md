# Fake Adapter

TODO: Start here!

Now that we've got an app that can display a list of recent earthquakes, let's add a bit of visual polish. Instead of dispaying three lines of text for each earthquake, let's display a nice list item view showing the magnitude, date, and location of the earthquake. We've added that layout at `res/layout/earthquake_list_item.xml`. We've also updated the `earthquake_activity.xml` layout with a linear layout to hold our list items.

So, our job is, for each earthquake we get back from USGS, inflate a new earthquake list item, fill in its `TextViews` with the appropriate information, then append that view to the end of the earthquake linear layout.

If you're thinking that this is what an `Adapter` is for, you're exactly right. We'll move this logic to an adapter in the next exercise.

## Location Formatting

You'll note that in our list item layout, we have two fields for the location. We have the location itself, which is the name of the landmark or city nearest the earthquake. Then we have the location offset, which describes the location of the earthquake relative to the landmark. For example, we might have a `location` of "Ndoi Island, Fiji", and a `locationOffset` of "67km NNE of" (NNE means north-north-east, or halfway between due north, and north-east). The USGS API reports the location as a single string of "67km NNE of Ndoi Island, Fiji", so we'll need to do some string handling work to split up the location and location offset.

There's another case we need to handle. When the earthquake is sufficiently close to a landmark, the API will just report the landmark, like "Pacific Rise". In that case, instead of splitting up the location and location offset, we'll just set the location offset to "Near the".

## Date Formatting

In our layout, we'd like to display the date and time on separate lines, so we'll need to update `Earthquake` with the new formatting.

# The Exercise

First modify `Earthquake` to format the location, location offset, and date/time correctly. Then update the `onLoadFinished()` method of `EarthquakeActivity` to create a new view for each earthquake. Check out the TODOs to get started!


You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/3.02-Exercise-FakeAdapter.zip), and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/3.02-Solution-FakeAdapter.zip). Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/3.02-Exercise-FakeAdapter...3.02-Solution-FakeAdapter).

