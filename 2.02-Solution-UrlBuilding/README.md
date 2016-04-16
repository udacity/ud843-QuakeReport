# URL Building!

TODO: Start Here!

Let's get into our first real exercise! Supplying a pre-built is a convenient way to get started, but it quickly leads to the same copy-paste pain we used Postman to deal with. Actually, the situation is worse when we're building an app, since we would really like to allow the user to modify the query on the fly, and there's no way we're going to make the user type in a URL. Fortunately, there are some Android classes that know all about how URLs are built, and will allow us to build up a URL from its component parts.

## URI vs URL

In this exercise we're going to use the `Uri.Builder` class to add query parameters to a base URL, much like we did using Postman. This saves us from having to fiddle with special characters while we're composing our URL, and allows us to programmatically decide what parameters to add.

You're probably wondering what a URI is, as opposed to a URL. A URL (uniform resource locator) is a specific type of URI (uniform resource identifier) that specifies not only what the resource _is_, but _how_ to retrieve it. More simply, a URL is a URI where the scheme (the bit right at the start before the colon), is `http` or `https`.

For more info the classes used to represent URLs and URIs, check out: http://developer.android.com/reference/java/net/URL.html and http://developer.android.com/reference/android/net/Uri.html

For more on the `Uri.Builder` class, check out: http://developer.android.com/reference/android/net/Uri.Builder.html

## Exceptions

Once we've build up our URI, we now need to transform it into a URL. There's a problem that can happen here, though. All URLs are URIs, but not all URIs are URLs. A URI could point to a file or some other type of resource that we can't get with an HTTP request.

The URL class has a constructor that takes a `String`, and when we call it, one of two things can happen. Either the String contains a valid URL, and the URL object is constructed correctly, or the String does not contain a valid URL... In which case we have a problem. The constructor could just return `null`, but for a constructor that's a recipe for very confusing errors. Fortunately, Java has a better solution.

When a Java method can fail, we say it "throws an exception". That means that the usual execution flow is interrupted, and the method doesn't return anything at all. Instead, it packages up information about why it failed into an `Exception` object (or a subclass), and "throws" it. It can then be "caught" by the method that called it (or the method that called that method, and so on). Actually, for exceptions like the `MalformedURLException` that the `URL` constructor can throw, our Java will yell about about uncaught exceptions until we've added the proper code to handle the error gracefully.

So! When we're using a method that can throw an exception, we write code like the following:

    try {
        someMethodThatCanFail()
    } catch (Exception e) {
        // Deal with the error, or at least log it.
    }

Exceptions are a very deep topic that we're only going to touch on just enough to write the app at hand. If you'd like to deep in deeper, this is a good place to start: https://docs.oracle.com/javase/tutorial/essential/exceptions/

# The Exercise

In this exercise, you'll first define some constants for the base query URL and query parameters, then build those components into a URI. Finally, you'll convert that URI into a URL, which will then be used to query USGS! Check out the TODOs pane at the bottom left of Android Studio to find your instructions.


https://github.com/udacity/ud843-QuakeReport/compare/2.02-Exercise-UrlBuilding...2.02-Solution-UrlBuilding
