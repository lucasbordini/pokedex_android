package com.bordini.lucas.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bordini.lucas.pokedex.api.APIInstance
import com.bordini.lucas.pokedex.api.Failure
import com.bordini.lucas.pokedex.api.Success
import com.bordini.lucas.pokedex.model.PokemonCard
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonCard>>(emptyList())
    val pokemonList: LiveData<List<PokemonCard>> get() = _pokemonList

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error

    fun fetchList() {
        viewModelScope.launch {
            when(val response = APIInstance.api.fetchPokemonList()) {
                is Success -> {
                    _pokemonList.postValue(response.data.data)
                }
                is Failure -> {
                    _error.postValue(response.cause?.message)
                }
            }
        }
    }
}