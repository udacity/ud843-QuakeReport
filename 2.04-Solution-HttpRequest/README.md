# HTTP Requests!

TODO: Start Here!

Now we're getting into the real core of this first lesson: making the HTTP request to get the data we want. In doing so we're going to cover a couple new concepts, including StringBuilder, InputStream, writing functions that throw exceptions, and the `finally` block.
 
## `StringBuilder`
 
`StringBuilder` is an incredibly useful class from the standard library that is a string that can be modified. You can append, insert, remove, and replace characters as you like, and then call `toString()` to get a frozen (in the business, we call it immutable) `String` back.

You can find more info here: http://developer.android.com/reference/java/lang/StringBuilder.html

## `InputStream`

Instead of returning a final result like a `String`, sometimes a function will return an `InputStream`. An input stream allows you to retrieve the information one chunk of data at a time. This is useful if the stream will be returning a ton of data, or if the stream represents some data source that will deliver data over time.

You can find more info on `InputStream` here: http://developer.android.com/reference/java/io/InputStream.html
 
Helping out with `InputStream` are `InputStreamReader` and `BufferedReader`. `InputStream` doesn't actually return characters we can read, like letters and numbers. Instead, it returns raw binary data (ones and zeros), with no specific instructions on what that data represents. That data might be an image, a sound, or some GeoJSON data for us to parse. In this case, we happen to know that the data combing back from our `InputStream` is indeed just text, and `InputStreamReader` handles that translation process from raw data to human-readable character.
 
You can find more info on `InputStreamReader` here: http://developer.android.com/reference/java/io/InputStreamReader.html

`InputStreamReader` only allows you to read a single character at a time, which can have some very rough performance implications, depending on how the underlying `InputStream` is actually providing the data. For instance, retrieving data from your computer's hard drive is quite fast if you're reading large chunks of data at once. If you try to read data from a hard drive a single character at a time, it'll take a very, very long time. This can be avoided by wrapping an `InputStreamReader` in a `BufferedReader`. `BufferedReader` will intercept your request for a character, and will instead read and save a larger chunk of data. When you go to request another character, the `BufferedReader` will then be able to satisfy your request from the data it read ahead of time.
 
A `BufferedReader` behaves more like a cache: https://en.wikipedia.org/wiki/Cache_(computing)

More info about `BufferedReader` can be found here: http://developer.android.com/reference/java/io/BufferedReader.html

### Side Sote: WHY?!?!

An `InputStream` wrapped in an `InputStreamReader` wrapped in a `BufferedReader`?! Surely there must be a simpler way to read data back from an `HttpURLConnection`. Indeed, a `readTheWholeResponseAsText()` method would be pretty convenient. The problem, though, is that that method wouldn't be reusable. Instead of pre-built end-to-end solutions, programming frameworks and libraries usually give you individual parts that can be combined together in many different ways. In this case the chain of streams and readers is kinda long, but in the future you'll encounter situations where you'll need to assemble these pieces in some different configuration. The flexibility you gain is absolutely worth the verbosity you need to endure right now.

## `HttpURLConnection`

Once you're got a `URL`, creating an `HttpURLConnection` is quite easy. Just call URL.openConnection(). Then, to actually fire the connection off and retrieve the results, you'll need to use `setRequestMethod()` set the HTTP request method, and call `connect()`. You can then call `getInputStream()` to get an `InputStream` containing the results.

You can find full documentation on `HttpURLConnection` here: http://developer.android.com/reference/java/net/HttpURLConnection.html

## Writing functions that throw exceptions

Say you're declaring a function that uses some code that can throw an exception. You can either wrap that code in a `try`/`catch` block and deal with the exception, or you can simply say that your function itself can throw an exception, and let the code that called your function handle it. The syntax is like so:

    public String myFunctionThatCanFail(String foo) throws IOException{
        ...
    }
    
The choice of whether to deal with an exception as soon as it arises, or pass it on to the calling function is a complicated one. If your function can recover from the error, definitely catch and deal with it. If it's unclear what your function should do about the error, you should probably pass it along until some other point where it's more clear to the app what should be done.

Here you can find the official Java tutorial on how to specify the exceptions thrown by a method: https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html

## The finally Block

There's one more piece to catching exceptions: the `finally` block. Sometimes we have code in a `try` block that absolutely needs to get run (such as code that cleans up resources we're using). However, if an exception happens, and we jump to the `catch` block, that essential code might get skipped. The solution is to move that code to a `finally` block following the `catch` block. Then when an exception happens, the code in the `finally` block will be executed before we jump to the `catch` block. In this exercise, we'll use a `finally` block ensure our `HttpURLConnection` is closed. 
 
# Putting it together

In this exercise, we'll open up a `HttpURLConnection`, and retrieve the resulting `InputStream`. We'll then wrap that stream in an `InputStreamReader` to get the actual characters, and wrap that in a `BufferedReader` for efficiency. Then we'll read each line from the `BufferedReader`, building up the result in a `StringBuilder`. In the meantime we'll catch and log any `IOExceptions`, and make sure that if we do hit an exception, our `HttpURLConnection` will be closed, so we don't leak resources.
 
That's quite the challenge, but the TODOs in the code will walk you through the process. Remember you can always check out the solution folder if you run into a task you're not sure how to handle.


