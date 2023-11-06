<p align="center"><img src="https://github.com/gongracr/RandomUserGallery/assets/2468164/c80e2538-2815-40e2-9fbd-603f48ab884e" alt="random user icon" width="200" height="200" style="text-align: center"/></p>

# GH Repositories Loader

This is a demo app to showcase how to display a paged list of public Github projects, sorted by number of stars in descending order using the [Github REST API](https://docs.github.com/en/rest). The app was designed and implemented trying to follow modern Android infrastructure, best development practices like Clean architecture and state of the art technical tools like Compose, Kotlin Flow and Hilt.

<img src="" width="240" align="right" hspace="10" >

## Main features 📲
- Pagination of endless items using androidx paging3 in a list gallery via Remote mediator
- Detailed screen showing more complete information about a specific Github project
- Ability to search/filter out projects by name

## Building tools 🛠️
* UI 
   * [Jetpack Compose](https://developer.android.com/jetpack/compose) declarative UI framework
   * [Coil Image Loading](https://coil-kt.github.io/coil/)
   * [Material design](https://material.io/design)

* Tech/Tools
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose) 
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
    * [Compose Destinations](https://github.com/raamcosta/compose-destinations) from Rafael Costa, a library for allowing an easier setup and usage of compose navigation under the hood
    * [Retrofit](https://square.github.io/retrofit/) for networking
    * [AndroidX Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) library for paging through list of items in an infinite scroll style

## Architecture 🏗️
  - MVVM Architecture (Model - ComposableView - ViewModel)
  - Clean architecture (Presentation - Domain - Data)
  - Repository pattern 
  - Hilt - dependency injection
  - Full [App Modularization](https://developer.android.com/topic/modularization). Internal data and domain purposes and responsibilities spread over several modules (`core` for the business/domain logic, `network` for interaction with REST api services and `persistence` for local storage).

<p align="center"><img src="" width="1200" /></p>
