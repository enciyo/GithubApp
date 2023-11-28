package com.enciyo.data

import android.content.Context
import com.enciyo.data.remote.FlipperProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Inject

class FlipperProviderImp @Inject constructor(@ApplicationContext private val context: Context) :
    FlipperProvider {
    override fun get(): Interceptor = Interceptor { chain ->
        chain.proceed(chain.request())
    }
}