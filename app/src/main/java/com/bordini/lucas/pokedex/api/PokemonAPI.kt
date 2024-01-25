package com.bordini.lucas.pokedex.api

import com.bordini.lucas.pokedex.model.PokemonResponse
import retrofit2.http.GET

interface PokemonAPI {

    @GET("/v2/cards")
    suspend fun fetchPokemonList(): APIResult<PokemonResponse>

}