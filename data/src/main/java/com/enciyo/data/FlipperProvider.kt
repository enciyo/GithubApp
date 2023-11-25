package com.enciyo.data

import okhttp3.Interceptor

interface FlipperProvider {
    fun get(): Interceptor
}


