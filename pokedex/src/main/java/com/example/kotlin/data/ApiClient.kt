package com.example.kotlin.data

import android.net.Uri
import com.example.kotlin.data.model.UriTypeAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {
    private val client: PokemonApi by lazy {
        initializeClient()
    }
    fun api(): PokemonApi {
        return client
    }

    private fun initializeClient(): PokemonApi {
        val gson = GsonBuilder()
        gson.registerTypeAdapter(Uri::class.java, UriTypeAdapter())
        val gsonConverterFactory = GsonConverterFactory.create(gson.create())
        return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .client(HttpClient.client)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }
}
