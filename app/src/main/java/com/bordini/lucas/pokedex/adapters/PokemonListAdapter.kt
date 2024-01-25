package com.bordini.lucas.pokedex.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bordini.lucas.pokedex.R
import com.bordini.lucas.pokedex.databinding.PokemonListRowBinding
import com.bordini.lucas.pokedex.model.PokemonCard
import com.squareup.picasso.Picasso

class PokemonListAdapter: RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    var items: List<PokemonCard> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val binding = PokemonListRowBinding.bind(view)

        fun set(card: PokemonCard) {
            Picasso.get()
                .load(card.images.small)
                .into(binding.ivPokemonImage)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_row, parent, false)
        return ViewHolder(view)
    }
}