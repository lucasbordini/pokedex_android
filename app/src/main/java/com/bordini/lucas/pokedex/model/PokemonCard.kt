package com.bordini.lucas.pokedex.model

data class PokemonCard(
    val id: String,
    val name: String,
    val images: PokemonCardImage,
) {

    data class PokemonCardImage(
        val small: String,
        val large: String,
    )
}