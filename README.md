# NIT3213 Final Assignment — Books App

**Student:** Louis Adriano
**Student ID:** s8112376
**Unit:** NIT3213 Mobile Application Development, Victoria University

## Description
An Android app with 3 screens: Login, Dashboard, and Details. Fetches book data from a REST API.

## Screens
- **Login** — authenticates user via POST request
- **Dashboard** — displays list of books in a RecyclerView
- **Details** — shows full book info including description

## Tech Stack
- Hilt (Dependency Injection)
- Retrofit + OkHttp (API calls)
- ViewModel + LiveData (state management)
- Coroutines (async)
- ViewBinding

## Architecture
MVVM with a data/ui/di package structure.

## How to Run
1. Clone the repo
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device (API 24+)
5. Login: username `s8112376`, password `Louis`

## Testing
Run unit tests with:
```bash
./gradlew test
```
