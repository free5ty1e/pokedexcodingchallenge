package com.example.kotlin.data

import com.example.kotlin.data.model.Pokemon
import com.example.kotlin.data.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    @GET("pokemon")
    suspend fun pokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
    ): Response<PokemonListResponse>

    @GET suspend fun pokemonByDirectUrl(@Url url: String): Response<Pokemon>

//    @GET("pokemon/{name}")
//    suspend fun pokemonByName(
//        @Path("name") name: String,
//    ): Response<Pokemon>
//
//    @GET("pokemon/{id}")
//    suspend fun pokemonById(
//        @Path("id") id: Int,
//    ): Response<Pokemon>

}