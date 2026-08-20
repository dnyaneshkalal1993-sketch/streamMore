# StreamMore - Premium OTT Streaming Platform

StreamMore is a modern, luxury-tier OTT streaming platform application built using **Kotlin Multiplatform (KMP)**. It features a cinematic user experience, including custom high-fidelity animations and a sleek dark theme inspired by premium entertainment services.

## 🚀 Tech Stack

### Shared Logic (KMP)
*   **Kotlin Multiplatform**: Shared business logic, networking, and data persistence across Android and iOS.
*   **Compose Multiplatform**: Declarative UI framework for building consistent interfaces across platforms.
*   **Koin**: Lightweight dependency injection framework.
*   **Ktor**: Asynchronous HTTP client for networking.
*   **Coil 3**: Image loading library for Compose Multiplatform.
*   **Room (KMP)**: Cross-platform database for local storage and "My List" functionality.
*   **Kotlinx Serialization**: Type-safe JSON serialization.
*   **Jetpack Navigation Compose**: Type-safe navigation within the app.

### Android Specific
*   **Android SplashScreen API**: Seamless handoff from system launch to custom animations.
*   **Core KTX**: Idiomatic Kotlin extensions for Android development.

## ✨ Key Features
*   **Cinematic Splash Screen**: A handcrafted 3-second 3D animation featuring the StreamMore brand identity.
*   **Premium Authentication**: Elegant Login and Sign Up screens with glassmorphism effects and soft red glows.
*   **Movie Discovery**: Browse trending, popular, and top-rated movies.
*   **Immersive Details**: High-resolution posters and integrated action bars.
*   **Search & Favorites**: Easily find content and manage your personal watchlist.

## 📸 Screenshots
| Splash Screen | Login | Sign Up |
| --- | --- | --- |
| <img src="screenshots/splash_screen.png" width="250"> | <img src="screenshots/login_screen.png" width="250"> | <img src="screenshots/signup_screen.png" width="250"> |

| Home Screen | Movie Details | My List |
| --- | --- | --- |
| <img src="screenshots/home_screen.png" width="250"> | <img src="screenshots/movie_details.png" width="250"> | <img src="screenshots/my_list.png" width="250"> |

## 🏗️ Architecture
The project follows **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern:
*   **data**: Implementation of repositories, local database, and remote API calls.
*   **domain**: Pure Kotlin business logic, including models and repository interfaces.
*   **ui**: Feature-based organization containing Screens, ViewModels, and shared components.

## 🛠️ Setup
1.  Open in **Android Studio Ladybug** or newer.
2.  Ensure you have the latest **Kotlin** and **Compose Multiplatform** plugins installed.
3.  Sync Gradle and run the `app` module on an emulator or physical device.

---
*Built with ❤️ using Kotlin Multiplatform.*
