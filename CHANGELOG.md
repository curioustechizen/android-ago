## v1.4.0

 - [FEATURE]: Ability to customize the relative time display text. This can be used for example to add prefixes or suffixes or return fully custom text
 - [FEATURE]: Updated the sample apps with new samples that demonstrate how to customize the text to display
 - [DEPRECATIONS]: Deprecated the `prefix` and `suffix` properties. These are not suitable for i18n.
 - [CHANGE]: Bumped the minSdk version of the **sample apps** to 14. Note that the minSdkversion of the library itself is still 8.

## v1.3.4

  - [FIX]: Issue [#47](https://github.com/curioustechizen/android-ago/issues/47)
  - [TRANSLATIONS]: Correct translations for Dutch, added Danish


## v1.3.3 [Apr 2, 2017]

  - [CHANGE]: Bumped versions of compileSdkVersion, targetSdkVersion, Gradle, Android Gradle plugin and buildToolsVersion
  - [TRANSLATIONS]: Spanish, Ukrainian, Norwegian, Arabic, Dutch, Romanian


## v1.3.2 [Jun 12, 2016]

  - [BUGFIX]: Fixed memory leak in `RelativeTimeTextView`
  - [TRANSLATIONS]: French, Portuguese


## v1.3.1 [May 15, 2016]

  - [TRANSLATIONS]: Hebrew, Russian, Chinese, Slovak, German


## v1.3.0 [Feb 14, 2014]

  - [CHANGE]: Set minSdk to 8
  - [CHANGE]: Cleaned up unnecessary resources
  - [MISC]: Update to latest build tools
  - [NEW]: Included tasks for publishing to Maven Central


## v1.2.0 [Dec 16, 2014]

  - [BUGFIX]: NPE seen when the RelativeTimeTextView has an initial visibility="gone"
  - [BUGFIX]: RelativeTimeTextView does not show anything if the reference time was only set using the XML attribute reference_time
  - [MISC]: Updated gradle files to 1.0.0


## v1.1.1 [Sep 2, 2014]

  - [FEATURE]: Added option to add prefix and suffix to the RelativeTimeTextView (Credit @xiprox )
  - [MISC]: Updated to recent versions of gradle, gradle plugin, build tools.



## v1.1.0 [Aug 21, 2014]

  - [FEATURE]: Added Gradle Support. (Credit to @intrications )
  - [BUGFIX]: #2
  - [BUGFIX]: #4

