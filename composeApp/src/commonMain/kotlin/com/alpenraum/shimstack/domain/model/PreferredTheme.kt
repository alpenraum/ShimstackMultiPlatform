package com.alpenraum.shimstack.domain.model

enum class PreferredTheme {
    LIGHT,
    DARK,
    AUTO;

    companion object {
        val default: PreferredTheme = AUTO
    }
}