## **Product Specification: Talk-to-me**

**Document Version:** 1.0

**Date:** June 16, 2025

**Author:** Google AI Studio

### 1. Overview & Vision

**Product Name:** Talk-to-me

**Elevator Pitch:** Talk-to-me is a personal automation app for Android that provides audible, context-aware alerts. Users define triggers based on time or location, and the app uses Text-to-Speech (TTS) to speak custom messages, helping users stay on track without looking at their screen.

**Target Audience:**
*   **Productivity Enthusiasts:** Individuals using techniques like Pomodoro or time-blocking.
*   **Commuters:** People who want alerts when they are nearing their home, office, or public transit stop.
*   **Users with Accessibility Needs:** Individuals who benefit from auditory cues over visual notifications.
*   **General Users:** Anyone needing simple, hands-free reminders (e.g., "It's 5 PM, time to leave work," or "You've arrived at the grocery store, don't forget the milk.").

### 2. Core Functional Requirements

We will refer to a single trigger-action pair as a **"Talk"**.

#### 2.1. Main Screen (Dashboard)
*   **FR-1.1:** The main screen shall display a list of all user-created Talks.
*   **FR-1.2:** Each list item must display:
    *   The core message text (or a title).
    *   An icon and short description representing the trigger (e.g., ⏰ "Every 30 minutes", 📍 "Arriving at Home").
    *   A prominent toggle switch (On/Off) to easily enable or disable the Talk.
*   **FR-1.3:** The screen must contain a clear, primary call-to-action (e.g., a Floating Action Button) to create a new Talk.
*   **FR-1.4:** Tapping on an existing Talk in the list shall navigate the user to the "Add/Edit Talk Screen" to modify it.
*   **FR-1.5:** A method to delete a Talk must be available (e.g., via a long-press context menu or a swipe-to-delete gesture).

#### 2.2. Add/Edit Talk Screen
*   **FR-2.1:** This screen shall function as a form to create or modify a Talk.
*   **FR-2.2:** **Trigger Selection:** The user must first select a trigger type:
    *   Time-Based
    *   Location-Based
*   **FR-2.3:** **Time-Based Trigger Configuration:** If "Time" is selected:
    *   The user must choose between "Interval" or "Specific Time".
    *   For "Interval," the user shall input a numeric value and select a unit (Minutes, Hours).
    *   For "Specific Time," the user shall use a standard time picker to select a time of day.
*   **FR-2.4:** **Location-Based Trigger Configuration (Geofence):** If "Location" is selected:
    *   The UI shall present a map view and a search bar.
    *   The user can define a location by searching for an address or dropping a pin on the map.
    *   The user must be able to specify the trigger condition: "On Enter" or "On Exit".
    *   The user must be able to define a radius for the geofence (e.g., 50m, 100m, 250m).
*   **FR-2.5:** **Action Configuration:**
    *   A text input field for the user to type the "Message to Speak".
    *   A "Test" button that uses TTS to speak the message aloud, allowing the user to preview it.
*   **FR-2.6:** The screen must have "Save" and "Cancel" actions. Saving will persist the Talk and activate its trigger if enabled.

#### 2.3. Action: Text-to-Speech (TTS)
*   **FR-3.1:** When a trigger condition is met, the app shall audibly speak the associated custom message.
*   **FR-3.2:** The TTS action must execute even if the app is in the background or the device is locked.
*   **FR-3.3:** The TTS action should use the user's default system TTS engine and voice settings initially.

#### 2.4. Settings Screen
*   **FR-4.1:** A global "Pause All Talks" switch to temporarily disable all active triggers without changing their individual on/off states.
*   **FR-4.2:** **TTS Settings:**
    *   Ability to select from the list of available TTS voices on the device.
    *   Sliders to adjust the default Speech Rate (speed).
    *   Sliders to adjust the default Pitch.
*   **FR-4.3:** A link to the system's application settings to manage Permissions.
*   **FR-4.4:** Standard "About," "Privacy Policy," and "Send Feedback" options.

### 3. Non-Functional Requirements

*   **NFR-1 (Performance):** The app UI must be responsive. Background processes should be highly optimized to have a negligible impact on device performance.
*   **NFR-2 (Battery Efficiency):** This is paramount. The app must use the most battery-efficient APIs available (`AlarmManager` for time, `GeofencingClient` for location) to minimize battery drain.
*   **NFR-3 (Reliability):** Triggers must be persistent. The app must automatically re-register all active Talks after the device reboots.
*   **NFR-4 (Offline Capability):** All core functionality (triggering, speaking) must work without an internet connection. An internet connection is only required for the initial setup of a location-based trigger (for address geocoding and map tiles).
*   **NFR-5 (Beauty):** The app shall look really nice and use the latest, most modern styles for Android.
*   **NFR-6 (Background Capability):** A Talk must also happen if the app is in the background; it cannot be killed by Android OS; think of an alarm clock thatalso fires 100% reliably.

### 4. Technical Specification

*   **Platform:** Android
*   **Minimum SDK:** Android 13
*   **Language:** Kotlin
*   **Architecture:** Model-View-ViewModel (MVVM)
*   **UI Toolkit:** Jetpack Compose
*   **Database:** Room Persistence Library for storing the `Talk` entities.
*   **Asynchronous Operations:** Kotlin Coroutines
*   **Dependency Injection:** Hilt (recommended)
*   **Key APIs:**
    *   **Time Triggers:** `AlarmManager`
    *   **Location Triggers:** `com.google.android.gms.location.GeofencingClient`
    *   **Action:** `android.speech.tts.TextToSpeech`
    *   **Background Execution:** `ForegroundService` (initiated by a `BroadcastReceiver`) to handle the TTS event reliably.

#### 4.1. Permissions & Justifications

The app must request the following permissions with clear, user-facing rationales:

| Permission                     | User-Facing Justification                                                                                                |
| ------------------------------ | -------------------------------------------------------------------------------------------------------------------------- |
| `ACCESS_FINE_LOCATION`         | "Required to find your current position when setting up a new location-based Talk."                                        |
| `ACCESS_BACKGROUND_LOCATION`   | "**Crucial for location alerts.** This allows 'Talk-to-me' to detect when you enter a saved area even when the app is closed." |
| `POST_NOTIFICATIONS` (Android 13+) | "Used to show important service status and to ensure the app can speak to you reliably from the background."               |
| `RECEIVE_BOOT_COMPLETED`       | "Allows the app to automatically restart your active Talks after you reboot your phone, so you don't miss anything."       |
| `SCHEDULE_EXACT_ALARM` (optional) | "Needed for precise time-based Talks to ensure they fire at the exact time you set."                                     |


### 6. Future Roadmap (V2.0 and Beyond)

*   **Advanced Triggers:**
    *   Wi-Fi network connection/disconnection.
    *   Bluetooth device connection/disconnection.
    *   Device charging state (plugged in/unplugged).
*   **Advanced Actions:**
    *   Play a custom sound file instead of TTS.
    *   Webhook integration to trigger IFTTT or other smart home services.
*   **Usability & Integration:**
    *   Home screen widgets to quickly toggle Talks.
    *   Cloud backup and sync of Talks across devices.
    *   Tasker plugin integration for power users.

---
