# Loaders

TODO: Start here!

`AsyncTask` is an incredibly useful class, but there are actually a couple of problems with using it to load data into the views of an app. The first problem is that if we hit the "Run Query" button a bunch of times, we'll create a bunch of copies of the same task, doing the same thing. If the first query takes a while, and the user get impatient and hits the button a few more times, that'll take up even more resources, and make the task take even longer.

The second problem is that since the `EarthquakeTask` has a reference to `EarthquakeActivity`, if we start a task, then close the app, the app's memory can't be freed for the system to use until the task is complete. Honestly, for an app of this level of complexity, these problems aren't huge, but they're worth fixing now, so that when you're building bigger apps that might need to be running many `AsyncTasks`, you know the right way to do it.

There's a piece of the Android framework specifically designed for getting loading up data for display in activities. It consists of a `LoaderManager`, which creates and destroys some set of `Loaders`. We'll be creating an `AsyncTaskLoader`, which is basically an `AsyncTask` wrapped in the logic that let's the `LoaderManager` handle it.
 
Loaders solve both problems mentioned above. When a loader is requested from the loader manager, the manager first checks to see if that loader has already been created, and passes back the existing copy if so. That means you can hit the query button as many times as you want, and the data will only be loaded once.

When an activity is destroyed, it will instruct its `LoaderManager` to first destroy all its loaders, which solves the second problem mentioned above.

Loaders are a quite sophisticated component, so don't worry if they seem a little black box-y for now. If you want to dig in more, check out this [developer guide](http://developer.android.com/guide/components/loaders.html), and this [documentation](http://developer.android.com/reference/android/content/AsyncTaskLoader.html) on `AsyncTaskLoader`.
 
## Using Loaders

There are three pieces we need to put together to swap out our `AsyncTask` for an `AsyncTaskLoader`. First, we need to write a subclass of `AsyncTaskLoader`, which we'll call `EarthquakeLoader`. The only interesting method is `loadInBackground`, which works just like `AsyncTask`'s `doInBackground`.
 
Next, we need to do some plumbing to allow our activity to use our loader. We do this by implementing an interface called `LoaderCallbacks`. The `LoaderManager` will call these methods when it wants to create our loader, or when our loader has results to deliver.

Finally, we need to have our query button request that the `LoaderManager` spin up an `EarthquakeLoader`.

# The Exercise

First, work through the TODOs in `EarthquakeLoader.java` to create our loader. Then tackle the TODOs in `EarthquakeActivity.java`. When you're done, watch the logs when you start up the app and press the query button. No matter how many times you hit the button, the network access will only happen once!

