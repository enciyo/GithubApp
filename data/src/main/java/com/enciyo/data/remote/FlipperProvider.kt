package com.enciyo.data.remote

import okhttp3.Interceptor

interface FlipperProvider {
    fun get(): Interceptor
}


