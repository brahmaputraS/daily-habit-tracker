# Daily Habit Tracker

A simple Android application to help you track and maintain your daily habits.

## Features

- Add, edit, and delete daily habits
- Mark habits as completed each day
- Calendar view to track your progress over time
- Customizable habit colors for visual organization
- Optional reminders for habits
- Dark/light mode support (follows system settings)
- Local storage for all your habit data



## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **UI Components**: Material Design, Navigation Component
- **Concurrency**: Kotlin Coroutines
- **Data Binding**: ViewBinding

## Project Structure

- **data**: Contains database, entities, DAOs, and repository
- **ui**: Contains activities, fragments, adapters, and ViewModels
- **utils**: Utility classes and extensions

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device (minimum SDK 26 - Android 8.0)

## Dependencies

- AndroidX Core and AppCompat
- Material Components
- ConstraintLayout
- Navigation Component
- Room Database
- Lifecycle Components (ViewModel, LiveData)

## Future Improvements

- Statistics and analytics for habit completion
- Habit categories and tags
- Export/import functionality
- Widget for quick habit tracking
- Cloud synchronization
