package com.example.kotlin.utility

import androidx.fragment.app.Fragment

object FragmentUtilities {

    fun Fragment.requireFragmentArg(key: String) = (requireArguments().getString(key)
        ?: throw IllegalArgumentException("Argument $key required"))
}
