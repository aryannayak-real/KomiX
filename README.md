# KomiX

KomiX is an Android manga reader derived from the open-source Komikku project.
This README replaces inherited project documentation for the KomiX distribution.

## Features

- Manga library management and chapter reading
- Browse sources and manage compatible extensions
- Download chapters for offline reading
- Reading progress and supported tracker integrations
- Reader and library settings

## Build

Install JDK 21 and the Android SDK, then build the existing debug application with
the Gradle wrapper:

```sh
./gradlew :app:assembleDebug
```

## Download a debug APK

In the GitHub repository, open **Actions**, select **Build KomiX APK**, and open
a successful run. Download the `KomiX-debug-APK` artifact from that run.

## License and attribution

KomiX is a derivative of Komikku and includes code from upstream projects,
including Mihon and TachiyomiSY. KomiX is not an official release of those
projects, and their contributors did not create the KomiX branding. The project
is distributed under the Apache License 2.0 in [LICENSE](LICENSE); retain all
upstream copyright, attribution, and third-party license notices. The README
and user-facing app icons are KomiX-specific replacements; upstream code and
compatibility identifiers remain in place.
