package com.alpenraum.shimstack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform