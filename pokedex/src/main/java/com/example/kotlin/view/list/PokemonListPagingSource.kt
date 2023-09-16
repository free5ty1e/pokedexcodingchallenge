package com.example.kotlin.view.list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlin.data.PokemonListRepository
import com.example.kotlin.data.PokemonRepository
import com.example.kotlin.data.model.PokemonListEntryResult

class PokemonListPagingSource : PagingSource<Int, PokemonListEntryResult>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonListEntryResult>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntryResult> {
        return try {
            // Start refresh at page 0 if undefined.
            val nextPageNumber = params.key ?: 0
            val response = PokemonListRepository.getPokemonListResults(nextPageNumber)

            // Also retrieve the detail and add the image URL to the result
            for (result in response.results) {
                val pokemon = PokemonRepository.getPokemonFromProvidedUrl(result.url)
                val frontDefaultUrl = pokemon.data?.sprites?.frontDefaultUrl
                Log.d("PokeListPS","Pokemon front sprite URL retrieved: $frontDefaultUrl")
                result.frontImageUrl = frontDefaultUrl
            }

            LoadResult.Page(
                data = response.results,
                prevKey = null, // Only paging forward.
                nextKey = response.nextPage
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }
}