# Implementation Plan - Movie Explorer (NewFlix) - CMP Version

Build a production-grade, cross-platform Movie & TV Shows explorer using **Compose Multiplatform**, following **MVI** and **Clean Architecture**. This plan incorporates the detailed feature set from the provided README while adapting the tech stack to the user's multiplatform requirements.

## User Review Required

> [!IMPORTANT]
> **Tech Stack Alignment**: I will use the user-requested stack (**Koin, Ktor, Coil 3**) instead of the README's suggested Hilt/Retrofit, as they are better suited for Compose Multiplatform.
>
> **Offline First (Room)**: The README specifies Room for local storage. I will use **androidx.room** (Multiplatform) to maintain the "Offline First" strategy.
>
> **TMDB API Key**: Placeholder will be provided in `local.properties`.

## Proposed Changes

### Project Structure & Configuration

I will transition the current Android-only project into a Compose Multiplatform structure.

#### [NEW] `shared` module
- **commonMain**: Core logic, Domain, Data, and shared UI (MVI).
- **androidMain**, **iosMain**, **desktopMain**, **wasmJsMain**: Platform-specific entry points and configurations.

#### [MODIFY] `app` module
- Android-specific wrapper for the shared CMP code.

---

### Architecture: MVI + Clean Architecture (Multiplatform)

#### Domain Layer (`shared/commonMain`)
- **Entities**: `Movie`, `TvShow`, `MovieDetails`, `Cast`, `Trailer`.
- **Use Cases**: `GetTrendingMovies`, `SearchMovies`, `GetFavorites`, `ToggleFavorite`.

#### Data Layer (`shared/commonMain`)
- **Ktor Network Client**: Configured for TMDB.
- **Room Database**: Multiplatform implementation for `Movie`, `Favorite`, and `RecentSearch` tables.
- **Repository Implementation**: "Offline First" logic (Network -> DB -> UI).

#### UI Layer (`shared/commonMain`)
- **MVI Components**:
    - `Intent`: `SearchIntent`, `FavoriteIntent`, `NavigateIntent`.
    - `State`: `HomeState`, `SearchState`, `DetailState`.
- **Screens** (Adapted from README):
    - `Home`: Trending, Popular, Top Rated, Now Playing.
    - `Details`: Rich info, Cast, Trailers (YouTube integration), Reviews, Recommendations.
    - `Search`: Real-time debounced search, Recent searches.
    - `Favorites/Watchlist`: Offline access to saved content.
- **Material 3**: Responsive layout for Phone, Tablet, Desktop, and Web.

---

### Features (From README)

1.  **Home**: Multiple horizontal carousels (Trending, Popular, etc.) + "Continue Watching" (Local DB).
2.  **Details**: Poster/Backdrop, Ratings, Genres, Cast & Crew, Trailers, and Recommendations.
3.  **Search**: Debounced real-time search with pagination (using Paging 3 Multiplatform).
4.  **Favorites**: Local storage for offline browsing.
5.  **Settings**: Theme (Light/Dark), Clear Cache.

## Verification Plan

### Automated Tests
- Unit tests for Use Cases and Reducers in `shared/commonMain`.
- Repository tests using MockKtor and Room in-memory DB.

### Manual Verification
- **Android**: Deploy to emulator/device.
- **Desktop**: Run `./gradlew :composeApp:run` (if available).
- **iOS**: Verify structure for Xcode integration.
