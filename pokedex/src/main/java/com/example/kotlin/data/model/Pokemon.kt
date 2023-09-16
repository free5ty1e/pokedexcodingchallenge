package com.example.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("abilities") val abilities: List<PokemonAbility>?,
    @SerializedName("sprites") val sprites: PokemonSprites,
    @SerializedName("id") val id: Int,
    @SerializedName("height") val height: Int,
)
