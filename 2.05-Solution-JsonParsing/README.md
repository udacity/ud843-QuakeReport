# JSON Parsing

TODO: Start here!

So far we've created the URL that queries USGS for an earthquake, and we've made an HTTP request which uses that URL to retrieve our earthquake as a chunk of text in GeoJSON format. It's time to dig into that data, and retrieve the particular facts we want. Then we'll create a new `Earthquake` class to hold onto those facts for display in our app. Let's take a look at an example (with some of the irrelevant keys omitted):

    {  
        "type":"FeatureCollection",
        "metadata":{ 
            ...
        },
        "features":[  
            {  
                "type":"Feature",
                "properties":{  
                    "mag":5.3,
                    "place":"149km N of Hihifo, Tonga",
                    "time":1459009128650,
                    ...
                },
                "geometry":{
                    "type":"Point",
                    "coordinates":[
                        -173.7788,
                        -14.5954,
                        24.77
                    ]
                },
                "id":"us20005cky"
            }
        ]
    }

If you haven't encountered JSON before, definitely go check out http://www.json.org/ for an introduction to how JSON is strctured.

The root JSON object has three keys, "type", "metadata", and "features". We're interested in the "features" key. The "features" key contains an array (you can tell because of the square brackets). It only has one object in it for now, but that'll change when we start querying for more than one earthquake. If we look at the object in the array, we see that it has four keys: "type", "properties", "geometry", and "id". Of those four, it looks like the "properties" key has the information we want. Finally, the object in the "properties" key has a bunch of keys, but the three we're interested in for now are "mag", "place", and "time".

So all we need to do is get the "features" key, get the first element of the array, get the "properties" key, and then get the "mag", "place", and "time" keys. That's reasonably straight forward, but there's a problem. The JSON we're looking at is just a string. It doesn't know anything about the concept of keys or arrays. Sounds like we might be in for a pretty terrible slog of searching the string for keys we care about...

## JSONObject, JSONArray, and JSONException

Fortunately, there's a way to make a simple string of JSON into a much more intelligent object that actually knows about the keys it contains and how to access their accompanying values. We can construct a `JSONObject` by passing in a `String`, and then call `get()` on the `JSONObject` to retrieve its values! The values can be other `JSONObject`s, `JSONArrays`, or individual chunks of data like Strings, or Integers. Note that any time we try a `get()` request on a `JSONObject`, the requested key might not exist, in which case the request will result in a `JSONException`, which we'll need to catch and deal with.

For more info on `JSONObject`, check out: http://developer.android.com/reference/org/json/JSONObject.html

## Unix Time and Time Formatting

We're set for extracting the data we want, but it looks like there might be a problem with the "time" key. The time 1459009128650 isn't in the most helpful format. This format is called Unix time, and it's describes a time by how many seconds had passed since midnight 1970 [universal coordinated time](https://en.wikipedia.org/wiki/Coordinated_Universal_Time) (actually, in GeoJSON, it's milliseconds). We'ed like to turn this Unix time into a more readable date and time, but that's a very tricky proposition. You'd probably want to display the time in the correct local time zone for the user, but time zones are [incredibly complex](https://www.youtube.com/watch?v=-5wpm-gesOY). Also depending on where you are in the world, dates are written differently.
  
Thankfully, you don't have to handle date formatting yourself. There's a fantastic class called `DateFormat` that knows all about time zones and how dates are written in different parts of the world, and will handle all this mess for you.

A quick glance over the documentation will get you right up to speed: http://developer.android.com/reference/java/text/DateFormat.html

For more info on Unix time, check out https://youtu.be/QJQ691PTKsA

# The Earthquake Object

Let's also plan out how we're going to store the earthquake data we'll return to the UI. We want to hold on to four `String`s: the magnitude, the location, the formatted date and time, and the raw JSON. The easiest way to do that is to create an object with a `public final` member variable for each of our strings, then set the strings in the constructor. We'll need to handle the date formatting there, and we also want to convert the magnitude to a string with a single decimal. We can handle this using `String.format()`. You'll find more specific instructions in the TODOs.
 
# The Exercise

This is the first time we've had multiple files with TODOs. When that happens, we'll give some hints on the recommended order to tackle the various tasks. In this case, it'll be helpful to complete the TODOs in `Earthquake.java` before moving on to `QueryUtils`. 

