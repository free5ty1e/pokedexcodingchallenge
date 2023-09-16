package com.example.kotlin.view.detail

import androidx.lifecycle.ViewModel
import com.example.kotlin.data.NetworkResource
import com.example.kotlin.data.PokemonRepository
import com.example.kotlin.data.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class PokemonDetailViewModel : ViewModel() {

    private val _pokemonDetailStateFlow = MutableStateFlow<NetworkResource<Pokemon>>(NetworkResource.Loading())
    val pokemonDetailStateFlow = _pokemonDetailStateFlow.asStateFlow()

    suspend fun generatePokemonDetailFlow(pokemonUrl: String) {
        _pokemonDetailStateFlow.value = PokemonRepository.getPokemonFromProvidedUrl(pokemonUrl)
    }
}
