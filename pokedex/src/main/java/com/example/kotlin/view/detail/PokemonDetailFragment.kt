package com.example.kotlin.view.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlin.data.NetworkResource
import com.example.kotlin.interfaces.PokeNavigationInterface
import com.example.kotlin.utility.FragmentUtilities.requireFragmentArg
import com.example.kotlin.view.components.ErrorScreen
import com.example.kotlin.view.components.LoadingScreen
import com.example.kotlin.view.components.PokemonDetailScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private val viewModel: PokemonDetailViewModel by viewModels()
    private var pokeNavigationInterfaceListener: PokeNavigationInterface? = null
    private lateinit var composeView: ComposeView

    // retrieve value from arguments in custom getter
    private val pokemonName: String
        get() = requireFragmentArg(ARG_POKEMON_NAME)

    private val pokemonUrl: String
        get() = requireFragmentArg(ARG_POKEMON_URL)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        composeView = ComposeView(requireContext()).apply { setContent { LoadingScreen() } }
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                generatePokemonDetailFlow(pokemonUrl)
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    // acting very much like livedata
                    pokemonDetailStateFlow.collectLatest {
                        composeView = when (it) {
                            is NetworkResource.Loading -> { // Not really necessary, already initialized to Loading
                                composeView.apply { setContent { LoadingScreen() } }
                            }

                            is NetworkResource.Success -> {
                                composeView.apply { setContent { PokemonDetailScreen(
                                    it.data,
                                    pokemonName,
                                    pokeNavigationInterfaceListener,
                                ) } }
                            }

                            is NetworkResource.Error -> {
                                composeView.apply { setContent { ErrorScreen("Error retrieving Pokemon: ${it.message}") } }
                            }
                        }
                    }
                }
            }
        }
        return composeView
    }

    /**
     * Assigning the navigation interface listener here to ensure it persists through
     * configuration changes
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PokeNavigationInterface) {
            pokeNavigationInterfaceListener = context
        } else {
            throw RuntimeException("$context must implement PokeNavigationInterface")
        }
    }

    /**
     * To avoid crashes with background processes completing after the fragment has been detached
     * from the context, we also set the listener to null here
     */
    override fun onDetach() {
        super.onDetach()
        pokeNavigationInterfaceListener = null
    }

    companion object {

        // the name for the argument
        private const val ARG_POKEMON_NAME = "argPokemonName"
        private const val ARG_POKEMON_URL = "argPokemonUrl"

        // Use this function to create instances of the fragment
        // and set the passed data as arguments
        fun newInstance(
            name: String,
            url: String,
        ) = PokemonDetailFragment().apply {
            arguments = bundleOf(
                ARG_POKEMON_NAME to name,
                ARG_POKEMON_URL to url,
            )
        }
    }
}
