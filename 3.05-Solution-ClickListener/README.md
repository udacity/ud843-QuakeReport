# Click Listener

It'd be nice to allow the user to click on a list item to learn more about that particular earthquake, but it'd be a ton of work to create a whole new layout to display more information. A much easier solution is to direct the user to the USGS web page for more information. To do that we'll need to add an `OnItemClickListener` to our `ListView`.

# The Exercise

First fix up `Earthquake` to accept a URL `String` argument, and have it convert that `String` to a `Uri` and hold onto it. Then upgrade `QueryUtils` to extract the USGS webpage URL for each earthquake. Finally, in `EarthquakeActivity`, finish up an `OnItemClickListener` that gets the `Earthquake` from the `EarthquakeAdapter`, and creates an implicit `Intent` to VIEW the earthquake's `Uri`.


You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/3.05-Exercise-ClickListener.zip), and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/3.05-Solution-ClickListener.zip). Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/3.05-Exercise-ClickListener...3.05-Solution-ClickListener).

