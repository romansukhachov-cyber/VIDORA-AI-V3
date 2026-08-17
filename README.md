# VIDORA AI v3

Android MVP for an AI video creation app.

## Included
- Home screen
- Video prompt editor
- Style selection
- 9:16 vertical format
- 15/30/60 second duration selection
- Demo generation progress
- Video library
- Placeholder backend endpoint via BuildConfig.VIDORA_API_BASE_URL
- GitHub Actions workflow that builds a debug APK

## Build
The project uses Android Gradle Plugin 9.3.0 and Java 17. Compose is explicitly disabled in this MVP because the current UI is implemented with standard Android Views; this avoids requiring the Kotlin 2.x Compose Compiler plugin.

For GitHub Actions: push the project to a repository with `app/` in the repository root, then open Actions -> VIDORA AI Android Build -> Run workflow. The APK is published as the `vidora-debug-apk` artifact.

The video generation is currently demonstrational. A secure backend must be connected before production AI generation is enabled.
