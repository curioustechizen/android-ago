# android-ago

This library provides `RelativeTimeTextView`, a custom `TextView` that takes a reference time and always displays the relative time with respect to the reference point, **automatically refreshing the display text as needed**. This is a common pattern seen in several apps like chat apps, social networking, email etc.

![Here is a screenshot from the sample app][1]

This library can be seen as a wrapper on top of the excellent `android.text.format.DateUtils` class. Note that the library does _not_ expose all the options provided by the `DateUtils` class. I have left out many features because I couldn't decide what would be the best way to achieve the flexibility - dozens of XML attributes? Contributions in this regard are welcome.


# Obtaining

### Gradle

Add the following to your build.gradle

    dependencies {
        compile 'com.github.curioustechizen.android-ago:library:1.3.3'
    }


### Eclipse+ADT
  1. Clone the repo
  2. In Eclipse, go to `File` -> `New` -> `Other`. Expand `Android` and select `Android Project from Existing Code`
  3. Browse to the `android-ago` sub-folder of the cloned repo and hit `Finish`

# Usage

  - Include `RelativeTimeTextView` in your layouts. 
  - Set the reference time either using `setReferenceTime` method or using the XML attribute `reference_time`.
  - Optionally, you can set a prefix using `relative_time_prefix` through XML or `setPrefix` from Java code.
  - Similarly, you can set a suffix using `relative_time_suffix` through XML or `setSuffix` from Java code.

In your layout:
```xml
<com.github.curioustechizen.ago.RelativeTimeTextView
    android:id="@+id/timestamp"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:relative_time_prefix="Completed "
    android:layout_marginTop="@dimen/margin_primary" />
```

In your Java code:
```java
RelativeTimeTextView v = (RelativeTimeTextView)findViewById(R.id.timestamp); //Or just use Butterknife!
v.setReferenceTime(new Date().getTime());
```

See the sample project for a concrete example.


## Why is this library even needed?

One might ask, why not just use `DateUtils` directly? Well, the answer is that the custom `TextView` provided by this library is responsible for keeping track of its own reference time and of updating the display text over regular periodic intervals. It is also responsible for scheduling (or cancelling a scheduled) update of the display text. All you have to do is set the reference time once.


## Who's Using this Library?

See [here](https://github.com/curioustechizen/android-ago/wiki/Apps-using-android-ago). If you would like to add your app to this list, please edit the wiki.


## Android version support statement

The library has been tested on API 11 and above. However, theoretically, it works on API 3 and above since all it uses is [DateUtils#getRelativeTimeSpanString](http://developer.android.com/reference/android/text/format/DateUtils.html#getRelativeTimeSpanString(long, long, long, int)).

The minSdkVersion has been set to 8, however do not expect support from me for API version < 11.


# Usage with Data Binding

See `android-ago-sample-databinding` for an example of how to use this library with the Android data binding library. Thanks to @Dev-IL for providing this sample.


### License

 
	Copyright 2017 Kiran Rao

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


  [1]: screenshots/android-ago-sample-screenshot.png "screenshot.png"


