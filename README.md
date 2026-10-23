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

Install JDK 21 and Android SDK Platform 36, then build the existing debug
application with the Gradle wrapper:

```sh
./gradlew :app:assembleDebug
```

## Download and install a debug APK

In the GitHub repository, open **Actions**, select **Build KomiX APK**, and open
a successful run. Download either `KomiX-universal-debug-APK` (recommended for
most phones) or `KomiX-arm64-v8a-debug-APK` (for ARM64 phones).

GitHub downloads each Actions artifact as a ZIP. On your phone, extract that ZIP
in the Files app and install the `.apk` inside it; do not try to install the ZIP
or a GitHub source-code archive. If Android blocks sideloading, allow the Files
app to install unknown apps, then retry the extracted APK.

## License and attribution

KomiX is a derivative of Komikku and includes code from upstream projects,
including Mihon and TachiyomiSY. KomiX is not an official release of those
projects, and their contributors did not create the KomiX branding. The project
is distributed under the Apache License 2.0 in [LICENSE](LICENSE); retain all
upstream copyright, attribution, and third-party license notices. The README
and user-facing app icons are KomiX-specific replacements; upstream code and
compatibility identifiers remain in place.
