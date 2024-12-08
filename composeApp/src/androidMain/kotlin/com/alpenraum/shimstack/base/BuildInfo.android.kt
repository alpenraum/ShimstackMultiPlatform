package com.alpenraum.shimstack.base

import com.alpenraum.shimstack.BuildConfig

actual object BuildInfo {
    actual fun isDebug() = BuildConfig.DEBUG
}
