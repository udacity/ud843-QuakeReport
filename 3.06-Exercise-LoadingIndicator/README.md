# Loading Indicator

TODO: Start Here!

In general, an app should always give the user some visual indication when something is going on. In this case, it'd be good to display a loading indicator when we're querying for earthquakes. `ListView`s have an `emptyView` that'll be displayed when there's nothing in the list, and that's a great place to put out loading indacator. We can actually pull double duty by putting a text view for displaying errors in there as well.

# The Exercise

We've already added a progress bar and error text view to the earthquake activity layout, so now all you have to do is set up the `ListView`'s empty view, and update our `displayError()` method to take advantage of the error text view. Note that we've added a three second delay to the loader to ensure the loading indicator shows up. Check out the TODOs to get started, and check out the solution if you run into trouble.


https://github.com/udacity/ud843-QuakeReport/compare/3.06-Exercise-LoadingIndicator...3.06-Solution-LoadingIndicator
