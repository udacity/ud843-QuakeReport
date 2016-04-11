# Regional Search

The next preference to add is the regional search, but there's some groundwork to lay before we tackle that. Up till now we've been searching for global earthquakes. If we want to search a certain region, we need to specify a latitude and longitude point, and a maximum radius from that point.

# The Exercise

First, we've added a bunch of region keys to `strings.xml`. We've also added a new `GeoCoordinate` class to hold onto latitude/longitude pairs. Finally, we added a couple new methods to `Utils` to create a `Map` that maps region keys to latitude/longitude pairs. There are a few TODOs in those two classes for you to finish up. The bulk of your task is in `QueryUtils`. First you'll need to check if we're doing a global or regional query. If it's regional, you'l then need to look up the latitude and longitude of the city of interest from the regions `Map`, and add the latitude, longitude, and a max radius to the query URL.
 
Check out the TODOs to get started!
