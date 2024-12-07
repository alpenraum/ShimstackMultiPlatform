package com.alpenraum.shimstack.base

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual object BuildInfo {
    actual fun isDebug() = Platform.isDebugBinary
}
