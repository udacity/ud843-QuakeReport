# HTTP Requests!

Now we're getting into the real core of this first lesson: making the HTTP request to get the data we want. In doing so we're going to cover a couple new concepts, including StringBuilder, InputStream, writing functions that throw exceptions, and the `finally` block.
 
## `StringBuilder`
 
`StringBuilder` is an incredibly useful class from the standard library that is a string that can be modified. You can append, insert, remove, and replace characters as you like, and then call `toString()` to get a frozen (in the business, we call it immutable) `String` back.

You can find more info here: http://developer.android.com/reference/java/lang/StringBuilder.html

## `InputStream`

Instead of returning a final result like a `String`, sometimes a function will return an `InputStream`. An input stream allows you to retrieve the information one chunk of data at a time. This is useful if the stream will be returning a ton of data, or if the stream represents some data source that will deliver data over time.

You can find more info on `InputStream` here: http://developer.android.com/reference/java/io/InputStream.html
 
Helping out with `InputStream` are `InputStreamReader` and `BufferedReader`. `InputStream` returns raw bytes of data, but in our case, we want the text characters that those bytes represent. `InputStreamReader` handles that translation process.
 
You can find more info on `InputStreamReader` here: http://developer.android.com/reference/java/io/InputStreamReader.html

`InputStreamReader` only allows you to read a single character at a time, which can have some very rough performance implications, depending on how the underlying `InputStream` is actually providing the data. `BufferedReader` will read ahead in the stream and store the results for quick retrieval.

More info about `BufferedReader` can be found here: http://developer.android.com/reference/java/io/BufferedReader.html


## `HttpURLConnection`

Once you're got a `URL`, creating an `HttpURLConnection` is quite easy. Just call URL.openConnection(). Then, to actually fire the connection off and retrieve the results, you'll need to use `setRequestMethod()` set the HTTP request method, and call `connect()`. You can then call `getInputStream()` to get an `InputStream` containing the results.

You can find full documentation on `HttpURLConnection` here: http://developer.android.com/reference/java/net/HttpURLConnection.html

## Writing functions that throw exceptions

Say you're declaring a function that uses some code that can throw an exception. You can either wrap that code in a `try`/`catch` block and deal with the exception, or you can simply say that your function itself can throw an exception, and let the code that called your function handle it. The syntax is like so:

    public String myFunctionThatCanFail(String foo) throws IOException{
        ...
    }

Here you can find the official Java tutorial on how to specify the exceptions thrown by a method:: https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html
 
# Putting it together

In this exercise, we'll open up a `HttpURLConnection`, and retrieve the resulting `InputStream`. We'll then wrap that stream in an `InputStreamReader` to get the actual characters, and wrap that in a `BufferedReader` for efficiency. Then we'll read each line from the `BufferedReader`, building up the result in a `StringBuilder`. In the meantime we'll catch and log any `IOExceptions`, and make sure that if we do hit an exception, our `HttpURLConnection` will be closed, so we don't leak resources.
 
That's quite the challenge, but the TODOs in the code will walk you through the process. Remember you can always check out the solution folder if you run into a task you're not sure how to handle.


