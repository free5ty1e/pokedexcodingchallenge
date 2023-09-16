package com.example.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.kotlin.interfaces.PokeNavigationInterface
import com.example.kotlin.view.detail.PokemonDetailFragment
import com.example.kotlin.view.list.PokemonListFragment

class PokemonActivity : PokeNavigationInterface, AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            addFragment(PokemonListFragment.newInstance(this), addToBackstack = false)
        }
    }

    private fun addFragment(fragment: Fragment, fragTag: String = "main", addToBackstack: Boolean = true) {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_up_in,
                R.anim.slide_down_out,
                R.anim.slide_up_in,
                R.anim.slide_down_out
            ).add(android.R.id.content, fragment, fragTag)
            if (addToBackstack) {
                addToBackStack(fragTag)
            }
        }
    }

    override fun navigateToPokemon(name: String, url: String) {
        Log.d("PokeActivity", "navigateToPokemon(name: $name, url: $url)...")
        addFragment(PokemonDetailFragment.newInstance(name, url), name)
    }

    override fun destroyPokemonDetailFragment(name: String) {
        Log.d("PokeActivity", "destroyPokemonDetailFragment(name: $name)...")
        supportFragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
