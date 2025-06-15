# Talk-to-me

This is a sample Android application implementing the basic structure described in `spec.md`.
The app is written in Kotlin using Jetpack Compose and Room. It allows creating
"Talks" that contain a message to be spoken aloud when triggered.

The project provides:

* Room database and DAO for persisting `Talk` entities.
* ViewModel and repository using Hilt for dependency injection.
* Simple Compose UI with a list of Talks and a form to add a new Talk.
* Text‑to‑Speech manager to speak messages.
* Time‑based triggers scheduled with `AlarmManager` and delivered via a
  foreground service.
* Boot receiver that restores active Talks after device reboot.
