package com.alpenraum.shimstack

import com.alpenraum.shimstack.base.logger.ShimstackLogger

class Greeting(
    private val logger: ShimstackLogger
) {
    private val platform = getPlatform()

    fun greet(): String {
        logger.i(Exception().stackTraceToString())
        return "Hello, ${platform.name}!"
    }
}