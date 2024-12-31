package com.alpenraum.shimstack

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val isAndroid = false
}

actual fun getPlatform(): Platform = IOSPlatform()