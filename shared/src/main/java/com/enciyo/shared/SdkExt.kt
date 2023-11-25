package com.enciyo.shared

import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(parameter = 0)
inline fun <T> runWithMinSdk(minVersion: Int, func: () -> T) {
    if (isMinSdk(minVersion)) {
        func()
    }
}

@ChecksSdkIntAtLeast(parameter = 0)
fun isMinSdk(minVersion: Int) = android.os.Build.VERSION.SDK_INT >= minVersion