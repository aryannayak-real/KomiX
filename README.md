# KomiX

<p align="center">
  <strong>Your manga. Your library. Your way.</strong>
</p>

<p align="center">
  An Android manga reader built for discovering, organizing, downloading, and reading manga.
</p>

<p align="center">
  <a href="https://github.com/aryannayak-real/KomiX/releases/latest">Download KomiX</a> ·
  <a href="https://github.com/aryannayak-real/KomiX/issues">Report an Issue</a> ·
  <a href="https://github.com/aryannayak-real/KomiX/actions">Automated Builds</a>
</p>

---

## About KomiX

KomiX is an Android manga reader designed to make managing and reading your manga collection convenient.

Organize your library, browse supported sources, download chapters for offline reading, and customize your reading experience using the features available in the application.

KomiX is an independent distribution derived from the open-source Komikku project, with its own branding and ongoing development.

## Features

- **Personal Manga Library** — Keep your manga collection organized.
- **Supported Sources** — Browse available sources and manage compatible extensions.
- **Offline Reading** — Download chapters and read them without an internet connection.
- **Reading Experience** — Use the available reader controls and settings.
- **Reading Progress** — Keep track of your reading and continue where you left off.
- **Library Management** — Manage your collection and access library tools.
- **Tracker Integration** — Connect supported tracking services where available.
- **Reader Customization** — Configure supported reading preferences.

*Feature availability depends on configured sources, extensions, and supported integrations.*

## Download

### Get the Latest Release

**[Download KomiX →](https://github.com/aryannayak-real/KomiX/releases/latest)**

Visit the official KomiX Releases page to find available versions and APK downloads.

### Choose Your APK

| APK | Recommended for |
|---|---|
| Universal | Most compatible Android devices |
| ARM64-v8a | Compatible 64-bit ARM Android devices |

If downloading from GitHub Actions, download the APK artifact ZIP, extract it, and install the `.apk` file inside. Do not attempt to install the ZIP itself.

## Installation

1. Download the appropriate APK.
2. Open the downloaded APK on your Android device.
3. If prompted, allow your browser or file manager to install unknown apps.
4. Return to the APK and complete installation.
5. Open KomiX and set up your manga library.

## Build from Source

### Requirements

- JDK 21
- Android SDK Platform 36
- Compatible Android SDK build tools

### Build a Debug APK

```bash
git clone https://github.com/aryannayak-real/KomiX.git
cd KomiX

chmod +x gradlew
./gradlew :app:assembleDebug
