# Welcome to Quake Report

It's time to start building our app for displaying information about recent earthquakes around the world. This is tough from a teaching perspective, because many different pieces all need to be working together before you see any results.
  
First, we need to create the URL that queries USGS for the data we want. Next we need to make an HTTP request using that URL. Then we need to parse the JSON data that USGS sends back. Finally we need to update that data in our app's UI. As an extra wrinkle, there's the issue of how to make sure our app stays responsive while we're making our HTTP request (we'll talk about that later in the lesson).

So! The plan for this lesson is to have you jump in to an established code base that is _almost_ working, and in each exercise it'll be up to you to add the missing pieces. We'll start super easy, by supplying everything but the URL.

## Importing the Exercise Project

First, though, you'll need to import this project into Android Studio. Open up Android Studio, and close any open projects until you get to the splash screen. On the Quick Start pane, select Import project. If you extracted the `ud843-QuakeReport-student` zip to the desktop, you can click the second button from the left to jump there. If you don't see the `ud843-QuakeReport-student` folder, you can click the blue circle arrows to refresh the file picker. Once you can see the `ud843-QuakeReport-student` folder, select the `2.01-Exercise-SupplyAUrl` directory and hit "OK".

Each exercise will consist of various tasks you'll need to complete, each of which will be tagged with a TODO comment. You can find all TODOs in the TODO pane at the bottom left of Android Studio. In fact, to find this file in Android Studio, you can open up the TODO pane, and find `README.md`. Hopefully you're now reading this file inside Android Studio.

To complete this exercise, double click on the TODO item in `EarthquakeActivity.java`, and follow the instructions in the comment you'll find there.





