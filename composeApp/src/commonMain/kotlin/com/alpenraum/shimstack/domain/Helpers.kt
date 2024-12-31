package com.alpenraum.shimstack.domain

import com.alpenraum.shimstack.base.BuildInfo
import kotlinx.coroutines.CancellationException

inline fun <reified EXCEPTION : Throwable> runWithErrorHandling(
    noinline loggingCallback: ((EXCEPTION) -> Unit)? = null,
    onlyThrowInDebug: Boolean = true,
    block: () -> Unit
) {
    try {
        block()
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        if (e is EXCEPTION) {
            if (shouldThrowException(onlyThrowInDebug)) throw e else loggingCallback?.invoke(e)
        }
    }
}

fun shouldThrowException(
    onlyThrowInDebug: Boolean,
    isDebugBuild: Boolean = BuildInfo.isDebug()
): Boolean = !(onlyThrowInDebug && !isDebugBuild)