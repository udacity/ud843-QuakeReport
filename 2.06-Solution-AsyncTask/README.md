# Async Task

TODO: Start here!

We've completed all the pieces that we need to retrieve and parse data from the internet! There's just a few housekeeping matters left to attend to.

## Permissions

First, accessing the internet is not an automatic right for an Android app. Accessing the internet is a capability that, if used maliciously, could potentially degrade the functioning of other apps, or burn through the user's data plan. If we want to use phone features that are critical to the user's security or privacy (like the microphone), we have to explicitly ask the user for permission. Accessing the internet, however, is a common enough (and low enough risk) permission that we can request that permission in our manifest, and the system will automaticially grant the permission.

There's a fantastic developer guide on the Android system permissions system, which you can read here: http://developer.android.com/guide/topics/security/permissions.html

Specifically, we need the `ACCESS_NETWORK_STATE` and `INTERNET` permissions.

## Threading

The second thing we need to figure out is how to actually call the networking code we wrote this lesson. You might think that we just put a call to our `getJSONFromWeb()` function in the query button's on-click method, but this actually causes a crash. The problem is that networking is often a long-running operation, and nothing else can happen in the app while you're waiting (possibly for a long time) for your query to finish. Fortunately, unlike humans, computers are great at multi-tasking.

The solution is to move the networking activity from the UI thread to a background thread. That's a very complex sentence, so let's break it down. When you read Java code, you read from the top of a method to the bottom, possibly looping or picking a particular branch, or jumping into and out of other methods. That's pretty much what a thread does: execute Java code in the usual order.

The interesting part is that a thread can be put on hold, so that another higher priority thread can do some work. Then the previous thread can be woken up again, and will pick right back up from where it left off.

Usually, all the code in your app is run on a single thread, called the UI thread. This is also the thread that responds to any user interaction with your app, like pressing buttons or scrolling. To run networking code, we need to create another thread, run the networking code there (where it can be interrupted by button presses or scrolling), and then return the results to the UI thread.

There's another developer guide on the topic of keeping your app responsive, which you can find here: http://developer.android.com/training/articles/perf-anr.html

## `AsyncTask`

Creating threads and passing information between threads is a very hard topic, so it's nice that Android has an incredibly convenient class for doing exactly what we need.
 
Read over the "Class Overview" and "The 4 steps" of AsyncTask, which you can find here http://developer.android.com/reference/android/os/AsyncTask.html

# The Exercise

First, visit `AndroidManifest.xml` and request the permissions we need for internet access. Then head over to `EarthquakeActivity.java` and finish `EarthquakeTask`. Then add code to `updateEarthquakeData()` to create and execute an `EarthquakeTask`.

Best of luck, and check out the solution project if you run into any trouble!
