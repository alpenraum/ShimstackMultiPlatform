package com.alpenraum.shimstack

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val isAndroid = true
}

actual fun getPlatform(): Platform = AndroidPlatform()