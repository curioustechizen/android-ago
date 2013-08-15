android-ago
==========

This library provides `RelativeTimeTextView`, a custom `TextView` that takes a reference time and always displays the relative time with respect to the reference point. This is a common pattern seen in several apps like chat apps, social networking, email etc.

![Here is a screenshot from the sample app][1]

This library can be seen as a wrapper on top of the excellent `android.text.format.DateUtils` class. Note that the library does _not_ expose all the options provided by the `DateFormat` class. I have left it out because I couldn't decide what would be the best way to achieve the flexibility - dozens of XML attributes? Contributions in this regard are welcome.


Why is this library even needed?
======

One might ask, why not just use DateUtils directly? Well, the answer is that the custom `TextView` provided by this library is responsible for keeping track of its own reference time and of updating the display text over regular periodic intervals. It is also responsible for scheduling (or cancelling a scheduled) update of the display text. All you have to do is include `RelativeTimeTextView` in your layouts, and set its reference time, either using `setReferenceTime(long)` or with the XML attribute `reference_time`.




  [1]: screenshots/android-ago-sample-screenshot.png "screenshot.png"


