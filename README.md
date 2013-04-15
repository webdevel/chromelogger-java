chromelogger-java
=================

## Overview

Chromelogger is a Java library for logging variables to the Google Chrome console using the Chrome Logger extension.

For more information about Chrome Logger check out [http://chromelogger.com](http://chromelogger.com).

This module is designed to be used during development and not in production.  It is not thread safe, and you do not want to risk leaking sensitive data to users!

## Getting Started

1. Install [Chrome Logger](https://chrome.google.com/extensions/detail/noaneddfkdjfnfdakjjmocngnfkfehhd) from the Chrome Web Store

2.  Click the extension icon to enable on the current tab's domain

    ![toggling](http://cdn.craig.is/img/chromelogger/toggle.gif)

3. Install the Java library

    ```java
    //TODO!
    ```
4. Download the [Apache Commons codec](http://commons.apache.org/proper/commons-codec/download_codec.cgi), and install in your build path.


4.  Start logging

    ```java
    // TODO!
    ```

## Sending Headers

Since every framework deals with setting headers slightly differently the library stores the header information and it is up to you to send it at the end of your request.


## API Documentation

The chromelogger module exposes some of the chrome logger methods.  The others will be coming in a future release.

### log(*args)
### warn(*args)
### error(*args)

Logs data to the console.  You can pass any number of arguments just as you would in the browser.

```java
// TODO!
```

### getVersion()

Outputs a string of the current version of this module

### getHeader(flush=true)

Returns a tuple with the header name and value to set in order to transmit your logs.

If ``flush`` argument is ``true`` all the data stored during this request will be flushed.

This is the preferred way to use this module.  At the end of each request you should call this method and add this header to the response.

```java
// TODO!
```

``getHeader()`` will return ``null`` if there is no data to log.

### setHeader = null

As an alternative to ``getHeader`` you can specify a function that can be used to set a header.  The function should accept two parameters (header name and value).  Usage would look something like:

```java
//TODO!
def set_header(name, value):
    # do stuff here to set header
    pass

chromelogger.set_header = set_header
```

When ``chromelogger.setHeader`` is not equal to ``null`` it will be called each time data is logged to set the header.  The class is a singleton so it will just keep overwriting the same header with more data as more data is added.

If you are going to use this you have to make sure to call ``reset()`` at the beginning of each request or at the end of each request in order to ensure the same data does not carry over into future requests.

### reset()

Clears out any data that has been set during this request.

