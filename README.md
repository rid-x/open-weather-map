# Weekly Forecast

<img src="app_logo.png" width="250" height="250" />

## About the Project
The project shows the weekly forecast of the user's current location. Project consists of two modules: the first is the main module that implements the UI and data flows, and the second defines the business logic.

`Note: The forecast data provider is Open Weather Map.`

## Download

<a href="https://play.google.com/store/apps/details?id=io.github.rid.hrant.weather" target="_blank"><img src="download_google_play.png"/></a>

## Architecture

The project uses two modern and powerful architectural patterns. Below you can find list of them.

- MVVM
- Clean Architecture

## Libraries and Frameworks

The application uses modern technologies offered by Google and JetBrains. Below you can find list of them.

- Retrofit
- OkHttp
- Flow
- Room
- Koin
- Coil
- Kotlin Coroutines
- Navigation Component

# Privacy Policy

## User Data
The app does not store personal data anywhere. It only stores the weekly forecast of the user's current location in the local database. 

## Permissions

### Internet

The app uses network to connect forecast data provider.

### Location
The app uses the user's location to get forecast data from `Open Weather Map` data provider.

### Network & WiFi States
The app uses network and Wifi status to decide where to get data from: from a local database or from an open weather map.

# License
```
Copyright 2021 Rid Hrant

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
