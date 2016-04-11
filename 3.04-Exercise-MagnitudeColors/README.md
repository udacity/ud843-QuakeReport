# Magnitude Colors

Setting up the earthquake adapter was a lot of work! Let's do something a little more straight forward now. The big red circle under the earthquake magnitude should change color based on the intensity of the earthquake. We've added different colors for magnitude 1 through 10 earthquakes in `colors.xml`, and we'll update `EarthquakeAdapter` to make the background of the magnitude text view the proper color!

# The Exercise

First update `Utils.java` to include a function for picking the correct color, then use that color in `EarthquakeAdapter.java`. Check out the TODOs to get started!
