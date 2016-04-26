# Map o' Parameters

TODO: Start Here!
 
Using `Uri.Builder` to create a URI (That we then can convert into a URL), is super convenient, but we can make it even better. Using the builder allowed us to avoid hard-coding the query URL. However, the query parameters were still hard-coded into the `buildUrl()` method, which isn't great. What if we wanted to retrieve the list query parameters and values from some other component of our app? What if we wanted to be able to change the query parameters on the fly (like to switch from a global to a regional query).

In this exercise, we'll separate the building of the URL from the selection of the query parameters, and in so doing, we'll learn about one of the most flexible data structures that Java has to offer, the `Map`.

## What's in a Map

Consider how the query parameters in a URL aren't the sort of data that fits very nicely into a list. There's a list of query parameters, but there's also a matching list of values that have a one to one relationship with the parameters. We could create a new class that holds onto this parameter/value pair, and then put those in a list, but there's a better way.
 
This pattern of a one to one relationship between some set of parameters (or keys) and values is so common that there's a built-in data type for just this purpose: `Map`. It's called a map because it allows you to look stuff up like a real-world map. You want to know where Kathmandu is? You look at a map, and the map tells you its latitude and longitude. More generally, a map contains some set of keys (no duplicates allowed), and for each key, returns a corresponding saved value.

Note that in Java `Map` is actually an interface, much like `List`, also like `List`, it's a generic interface, which means that the class of the keys and values can be set when the `Map` is created. Let's say we wanted to create a map from the name of a city (a `String`) to that city's location (let's say that's packaged into a `GeoLocation` object). We would create such a map like so:

    HashMap<String, GeoLocation> myMap = new HashMap<>();
    
Just like we can't create a `List` directly, but can create an `ArrayList`, we similarly can't create a `Map`. There are a number of classes that implement that `Map` protocol, and for this class we'll use `HashMap`.

There are three other important things to know about a map. First, `myMap.put(key, value)` let's you add new entries to the map. Second, `myMap.get(key)` returns the associated value. And finally, `myMap.keySet()` returns an iterable collection of the keys that are present in the map.

For more information about `Map`s, check out: https://docs.oracle.com/javase/7/docs/api/java/util/Map.html

## Iteration

That last point could use some expanding. A collection than implements the `Iterator` interface is simply one that can be used in a for-each statement. A for-each loop is an easy way to perform an operation for each element in a collection. It looks like this:

    for (String param : paramMap.keySet()) {
        // Do some stuff with each parameter
    }

For more info, check out: https://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html and https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html


# The Exercise at Hand

In this exercise, you'll fill in a function that creates a `HashMap<String, String>` that holds our query parameters, and you'll modify the `buildUrl()` function to process that `Map` into a URL. Check out the TODOs at the bottom of your Android Studio window to find your specific instructions!


You can download a zip of this exercise [here](https://github.com/udacity/ud843-QuakeReport/archive/2.03-Exercise-ParameterMap.zip), and a zip of the solution [here](https://github.com/udacity/ud843-QuakeReport/archive/2.03-Solution-ParameterMap.zip). Also, you can find a visual summary of the solution [here](https://github.com/udacity/ud843-QuakeReport/compare/2.03-Exercise-ParameterMap...2.03-Solution-ParameterMap).

