package com.example.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    @SerializedName("ability") val ability: PokemonListEntryResult,
    @SerializedName("is_hidden") val isHidden: Boolean,
    @SerializedName("slot") val slot: Int,
)
