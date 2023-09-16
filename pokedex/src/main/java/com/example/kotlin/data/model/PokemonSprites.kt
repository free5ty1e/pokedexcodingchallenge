package com.example.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSprites(
    @SerializedName("front_default") val frontDefaultUrl: String?,
    @SerializedName("back_default") val backDefaultUrl: String?,
)
