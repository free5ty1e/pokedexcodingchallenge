package com.example.kotlin.view.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kotlin.R
import com.example.kotlin.data.model.PokemonListEntryResult
import com.example.kotlin.interfaces.PokeListButtonInterface


class PokemonPagingListAdapter(
    private val pokeListButtonInterface: PokeListButtonInterface,
) :
    PagingDataAdapter<PokemonListEntryResult, PokemonPagingListAdapter.PokemonListEntryViewHolder>(
        PokemonDiffer
    ) {

    override fun onBindViewHolder(holder: PokemonListEntryViewHolder, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListEntryViewHolder {
        return PokemonListEntryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_pokemon_list_entry, parent, false),
            pokeListButtonInterface,
        )
    }

    class PokemonListEntryViewHolder(
        private val view: View,
        private val pokeListButtonInterface: PokeListButtonInterface?,
    ) : RecyclerView.ViewHolder(view) {
        fun bindView(item: PokemonListEntryResult) {
            val pokeButton = view.findViewById<Button>(R.id.pokemon_name)

            // Load poke front sprite icon as preview in list
            val pokeIcon = view.findViewById<ImageButton>(R.id.pokemon_list_icon)
            pokeButton.text = item.name
            Log.d("PokeListVH", "Loading Pokemon ${item.name}'s front sprite from ${item.frontImageUrl}")
            pokeIcon.load(
                item.frontImageUrl,
                builder = {
                    placeholder(android.R.drawable.btn_star)
                    crossfade(true)
                    error(android.R.drawable.stat_notify_error)
                }
            )

            val pokemonListEntryClickListener: (v: View) -> Unit = {
                pokeListButtonInterface?.onClick(item.name, item.url)
            }
            pokeButton.setOnClickListener(pokemonListEntryClickListener)
            pokeIcon.setOnClickListener(pokemonListEntryClickListener)
        }
    }

    object PokemonDiffer : DiffUtil.ItemCallback<PokemonListEntryResult>() {
        override fun areItemsTheSame(
            oldItem: PokemonListEntryResult,
            newItem: PokemonListEntryResult
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: PokemonListEntryResult,
            newItem: PokemonListEntryResult
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.url == newItem.url
        }
    }
}