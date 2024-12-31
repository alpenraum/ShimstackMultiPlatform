package com.alpenraum.shimstack

interface Platform {
    val name: String
    val isAndroid: Boolean
}

expect fun getPlatform(): Platform