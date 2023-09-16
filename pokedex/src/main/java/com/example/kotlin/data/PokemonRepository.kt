package com.example.kotlin.data

import com.example.kotlin.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PokemonRepository : BaseSafeRepository() {

    suspend fun getPokemonFromProvidedUrl(url: String): NetworkResource<Pokemon> =
        withContext(Dispatchers.IO) {
            val api: PokemonApi = ApiClient.api()
            safeApiCall { api.pokemonByDirectUrl(url) }
        }
}
