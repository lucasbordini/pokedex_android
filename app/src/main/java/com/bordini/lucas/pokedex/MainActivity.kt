package com.bordini.lucas.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bordini.lucas.pokedex.adapters.PokemonListAdapter
import androidx.activity.viewModels
import com.bordini.lucas.pokedex.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter = PokemonListAdapter()

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupObservers()
        viewModel.fetchList()
    }

    private fun setupObservers() {
        viewModel.pokemonList.observe(this) {
            if (it.isNullOrEmpty()) { return@observe }
            adapter.items = it
            binding.rvPokemonList.visibility = View.VISIBLE
            binding.pgLoading.visibility = View.GONE
        }
        viewModel.error.observe(this) {
            if (it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).show()
            }
        }
    }

    private fun setupView() {
        binding.rvPokemonList.adapter = adapter
    }


}