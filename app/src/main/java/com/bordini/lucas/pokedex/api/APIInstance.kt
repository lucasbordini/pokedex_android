package com.bordini.lucas.pokedex.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIInstance {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    val api: PokemonAPI by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.pokemontcg.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(APICallAdapter.Factory())
            .build()
            .create(PokemonAPI::class.java)
    }
}