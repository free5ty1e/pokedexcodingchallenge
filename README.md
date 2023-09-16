# Android PokeDex Coding Challenge

Exercise 2023.09: Create a simple Pokedex using the PokeDex API here ( https://pokeapi.co/ ) using Fragments.  Show a paged list of pokemon (there are well over 2000 now!) - each item should show the name and an image.  When the user taps a Pokemon they will see a different fragment showing name, id, height, first ability name, and front / back images.  Use Retrofit for the network requests.
* I decided to show off the interoperability of Jetpack Compose (and the flexibility of Material 3’s BottomSheet) here by pretending that we have an existing codebase leveraging fragments and we want to start using Compose in our app without converting the entire thing to Compose yet.  
* I also utilized Kotlin Coroutines and Flow to easily manage the network requests as needed, and Coil for ultra simple asynchronous image loading.
  * Pokemon List Fragment uses standard XML UI.  There is a chain of network requests here organized into Flows that happens as the user scrolls:
    * One request for the pages of the Pokemon list.  The only details provided from this request are `name` and a detail `url`.  Subsequent pages are loaded as the user reaches the bottom of the list, at first only the first page of 20 Pokemon are loaded.
    * Another request to retrieve the details from the given `url` for each, which in turn contains sprite PNG links.
    * Finally, this sends Coil off requesting the preview thumbnail from the detail object’s `sprite_front` PNG URL, which then shows up in the list as soon as it is available.
  * Pokemon Detail Fragment uses Jetpack Compose’s ComposeView and Material 3’s BottomSheet to overlay a detail fragment over top of the bottom part of the List Fragment.  
  * The user can still scroll through the paged list and choose other Pokemon to open more detail bottom sheets for.  They will simply stack until dismissed by swiping down or using the system Back button.
* Demo video: https://drive.google.com/file/d/1mO7OMYgA7Vcw9MHq5_gJje-K2Q6JuPdl/view?usp=sharing
* APK: https://drive.google.com/file/d/1Pq8ZlMepiAdUI4gmJ5rjkwlkbU7UKHWW/view?usp=sharing 
