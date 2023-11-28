# SatellitesApp


<!-- TOC -->
* [SatellitesApp](#satellitesapp)
* [Images](#images)
* [Introduction](#introduction)
  * [Built With 🛠](#built-with-)
  * [Third Party](#third-party)
<!-- TOC -->
- You can follow project [Project Board](https://github.com/users/enciyo/projects/4)
- You can see design below [Project Design Figma](https://www.figma.com/file/MIsFyDEJhKm1DrEsxGmCr0/Material-3-Design-Kit-(Community)?type=design&node-id=54695-24851&mode=design&t=9jEE9UF01hJIq7BV-0)


# Images
<img height="452" src="images%2FHome.png" width="214"/><img height="452" src="images%2FHome_favorites.png" width="214"/><img height="452" src="images%2FSearch.png" width="214"/><img height="452" src="images%2FHome_result.png" width="214"/>


# Introduction

This project is architect using the Uncle Bob's clean architecture approach. [UncleBobClean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.

## Third Party
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate) - Manages ViewBinding lifecycle and clears the reference to it to prevent memory leaks
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android





