package com.alpenraum.shimstack.ui.base.navigation

import androidx.navigation.NavOptions

inline fun <reified T : Any> popUpToNavOptions(inclusive: Boolean = true) =
    NavOptions
        .Builder()
        .apply {
            setPopUpTo<T>(inclusive = inclusive)
        }.build()