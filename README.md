# android-ago

This library provides `RelativeTimeTextView`, a custom `TextView` that takes a reference time and always displays the relative time with respect to the reference point, **automatically refreshing the display text as needed**. This is a common pattern seen in several apps like chat apps, social networking, email etc.

![Here is a screenshot from the sample app][1]

This library can be seen as a wrapper on top of the excellent `android.text.format.DateUtils` class. Note that the library does _not_ expose all the options provided by the `DateUtils` class. I have left out many features because I couldn't decide what would be the best way to achieve the flexibility - dozens of XML attributes? Contributions in this regard are welcome.

## Why should I use this instead of DateUtils class?

Because this library **automatically refreshes the display text as needed**. It internally uses `DateUtils` class.

Imagine you use `DateUtils` directly without using this library.
  - Imagine that it is **9 am** now. You set a reference time of **9:05 am**. Your TextView displays `in 5 mins`
  - Now the time becomes **9:01 am**. You still display `in 5 mins` even though you should be showing `in 4 mins`
  
To do this correctly, you will need to keep refreshing the text views every minute. However, even that is not necessary. If the reference time is 3 hours from now, you only need to refresh every hour - not every minute.

This library handles all of this for you. `RelativeTimeTextView` automatically refreshes the display text _**only as often as necessary**_.


# Obtaining

### Gradle

Add the following to your build.gradle

    dependencies {
        compile 'com.github.curioustechizen.android-ago:library:1.4.0'
    }

**Important:** v1.3.4 Fixed a major bug (#47). If you are using an older version, please update to at least 1.3.4 now.

### Eclipse+ADT
  1. Clone the repo
  2. In Eclipse, go to `File` -> `New` -> `Other`. Expand `Android` and select `Android Project from Existing Code`
  3. Browse to the `android-ago` sub-folder of the cloned repo and hit `Finish`


# Usage
  - Include `RelativeTimeTextView` in your layouts. 
  - Set the reference time either using `setReferenceTime` method or using the XML attribute `reference_time`.
  
In your layout:
```xml
<com.github.curioustechizen.ago.RelativeTimeTextView
    android:id="@+id/timestamp"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/margin_primary" />
```

In your Java code:
```java
RelativeTimeTextView v = (RelativeTimeTextView)findViewById(R.id.timestamp); //Or just use Butterknife!
v.setReferenceTime(new Date().getTime());
```

See the sample project for a concrete example.

## Customization

By default, this library simply calls `DateUtils.getRelativeTimeSpanString`. This might not be sufficient for you. For example, you might need to add a prefix. RTTV provides a hook for such cases - the `getRelativeTimeDisplayString` method. You can override this method and add whatever prefixes or suffixes you need.

Here is a simple example:

```xml
<!-- strings.xml -->
<string name="format_relative_time_with_prefix">Updated %1$s</string>
```

```java
class PrefixRttv extends RelativeTimeTextView {
    @Override
    protected CharSequence getRelativeTimeDisplayString(long referenceTime, long now) {
        final String relativeTime = super.getRelativeTimeDisplayString(referenceTime, now);
        return getResources.getString(R.string.format_relative_time_with_prefix, relativeTime);
    }    
}
```

More examples in the sample project

## Advanced customization

What if the string returned by `DateUtils.getRelativeTimeSpanString` does not suit you? Well, you can still use RTTV for its auto-refresh capability and take over complete control of the display string itself. Simply override `getRelativeTimeDisplayString` and don't call through to the `super`  method. Instead, perform your own logic and return whatever string you wish here.

```xml
<!-- strings.xml -->
<string name="future">Some day, in the distance future</string>
<string name="past">Once upon a time, long long ago</string>
<string name="now">Right NOW!</string>
```

```java
class FullyCustomRttv extends RelativeTimeTextView {
    @Override
    protected CharSequence getRelativeTimeDisplayString(long referenceTime, long now) {
        //Notice that we don't call super here.
        int resourceId = 0;
        if(referenceTime == now) resourceId = R.id.now;
        else if(referenceTime > now) resourceId = R.id.future;
        else resourceId = past;
        
        return getResources().getString(resourceId);
    }    
}
```

See the examples in the sample project for more details.

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


  [1]: screenshots/android-ago-sample-screenshot_1.4.0.png "screenshot.png"


