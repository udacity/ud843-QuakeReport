# Earthquake List

TODO: Start Here

In the last lesson, we completed a minimal working example of an app that could query the internet for an earthquake. Now let's start moving in the direction of our compete app, which displays lists of earthquakes.

## Retrieving Multiple Earthquakes

The first step will be to modify `EarthquakeAdapter` and associated `QueryUtils`. We can get multiple earthquakes from USGS just by bumping up the `limit` query parameter, but we'll also need to modify our GeoJSON parser to be able to return a `List<Earthquake>`, instead of a single `Earthquake`. Then there's a somewhat tedious process of making `EarthquakeAdapter` a subclass of `AsyncTaskLoader<List<Earthquake>>`.

By the way, if you haven't seen that before, it's totally possible to have nested generics. The way to read this is: "Alright, we have an AsyncTaskLoader. What does it load? A List. A list of what? A list of Earthquakes."

## Displaying Multiple Earthquakes

We'll be rigging up a a `ListView` and `Adapter` shortly, but for now, we'll just dump the data from all the earthquakes into one giant scrollable text field. This is clearly not the right way to do things in a production app, but it's useful to know quick and dirty ways to do things for debug and development purposes.

# The Exercise

Start in `QueryUtils`, then fix up `EarthquakeAdapter`, and the callbacks in `EarthquakeActivity`. Finally set up `EarthquakeActivity` to display a janky list of earthquakes. Check out the TODO pane at the bottom left of Android Studio to get started!



https://github.com/udacity/ud843-QuakeReport/compare/3.01-Exercise-EarthquakeList...3.01-Solution-EarthquakeList
