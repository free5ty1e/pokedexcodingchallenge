package com.example.kotlin.view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin.data.model.Pokemon
import com.example.kotlin.interfaces.PokeNavigationInterface

@OptIn(ExperimentalMaterial3Api::class) //Required to try BottomSheetScaffold as is will probably change - should not use this in production
@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon?,
    pokemonName: String,
    pokeNavigationInterfaceListener: PokeNavigationInterface?,
    modifier: Modifier = Modifier
) {
    pokemon?.let { definitelyAPokemon ->
        Log.d(
            "PokeDetail",
            "pokemon $pokemonName loading front image ${definitelyAPokemon.sprites.frontDefaultUrl} and back image ${definitelyAPokemon.sprites.backDefaultUrl}"
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                false,
                SheetValue.Expanded,
                confirmValueChange = {
                    if (it == SheetValue.Hidden) {
                        Log.d(
                            "PokeDetail",
                            "Detail fragment for Pokemon $pokemonName swiped away / hidden, destroying fragment..."
                        )
                    }
                    pokeNavigationInterfaceListener?.destroyPokemonDetailFragment(pokemonName)
                    true
                }
            )
        )
        BottomSheetScaffold(
            sheetPeekHeight = 0.dp,
            scaffoldState = scaffoldState,
            modifier = modifier,
            sheetContent = {

                // Bottom sheet content
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Pokemon name: $pokemonName",
                        modifier = modifier
                    )
                    Text(
                        text = "ID = ${definitelyAPokemon.id}",
                        modifier = modifier
                    )
                    Text(
                        text = "Height = ${definitelyAPokemon.height}",
                        modifier = modifier
                    )
                    Text(
                        text = "First ability name = ${definitelyAPokemon.abilities?.get(0)?.ability?.name}",
                        modifier = modifier
                    )
                    definitelyAPokemon.sprites.frontDefaultUrl?.let { spriteUrl ->
                        PokemonSprite(
                            spriteTitle = "Front",
                            spriteUrl = spriteUrl,
                            contentDescription = "Pokemon Sprite Front",
                            modifier = modifier
                        )
                    }
                    definitelyAPokemon.sprites.backDefaultUrl?.let { spriteUrl ->
                        PokemonSprite(
                            spriteTitle = "Back",
                            spriteUrl = spriteUrl,
                            contentDescription = "Pokemon Sprite Back",
                            modifier = modifier
                        )
                    }
                    Spacer(modifier = modifier.height(8.dp))
                }
            }
        ) {
            // Scaffold content - no content / transparent.
            // We want to see through this to the Pokedex list beneath.
        }
    }
}


