package com.example.kotlin.view.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.kotlin.data.model.PokemonListEntryResult
import com.example.kotlin.interfaces.PokeListButtonInterface
import com.example.kotlin.interfaces.PokeNavigationInterface
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {


    private val adapter: PokemonPagingListAdapter?
        get() = view?.findViewById<RecyclerView>(R.id.pokemon_list)?.adapter as? PokemonPagingListAdapter

    private val viewModel: PokemonListViewModel by viewModels()

    private var pokeNavigationInterface: PokeNavigationInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
        view.findViewById<RecyclerView>(R.id.pokemon_list).apply {
            adapter = PokemonPagingListAdapter(object : PokeListButtonInterface {
                override fun onClick(name: String, url: String) {
                    Log.d("PokeListFrag", "onClick($name, $url)")
                    pokeNavigationInterface?.navigateToPokemon(name, url)
                }
            })
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch { viewModel.pokemonFlow.collectLatest { populateList(it) } }
    }

    private suspend fun populateList(results: PagingData<PokemonListEntryResult>) {
        adapter?.submitData(results)
    }

    companion object {
        fun newInstance(pokeNavigationInterface: PokeNavigationInterface): Fragment {
            val pokemonListFragment = PokemonListFragment()
            pokemonListFragment.pokeNavigationInterface = pokeNavigationInterface
            return pokemonListFragment
        }
    }
}