This file is mainly for me to remember how to publish the library on Maven Central. I do not do this very often (perhaps a couple of times a year) and it is easy to forget the procedure.

1. Make sure the version numbers and version codes are updated in android0-ago/build.gradle and also in android-ago/maven-publish.gradle
2. Once you have tested all the changes and are sure you want to publish, run `./gradlew uploadArchives`
3. Log in to https://oss.sonatype.org/#stagingRepositories and scroll down or search for android-ago
4. Select the android-ago repository and Click "Close" button at the top.
5. After a few minutes if you refresh the closed repo, you will see the "Release" and "Drop" buttons enabled. At this point, select "Release".
6. After a few minutes you will no longer see the repo in Staging Repositories section. This means it has been released. To confirm, go to the "artifact-search" box and search for android-ago. Ensure that the latest version is available.
7. It takes 10 minutes for the library to show up on the [Maven Central Repository](http://repo1.maven.org/maven2) and about 3 hours to show up on [Maven Search](https://search.maven.org/).