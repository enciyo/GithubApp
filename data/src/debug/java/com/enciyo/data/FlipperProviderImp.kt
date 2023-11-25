package com.enciyo.data

import android.content.Context
import android.os.Build
import com.enciyo.shared.runWithMinSdk
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Inject

class FlipperProviderImp @Inject constructor(@ApplicationContext private val context: Context) :
    FlipperProvider {

    override fun get(): Interceptor {
        val networkInspector = NetworkFlipperPlugin()
        val descriptorMapping = DescriptorMapping.withDefaults()
        runWithMinSdk(Build.VERSION_CODES.LOLLIPOP_MR1) {
            SoLoader.init(context, false)
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(networkInspector)
            client.addPlugin(InspectorFlipperPlugin(context, descriptorMapping))
            client.start()

        }
        return FlipperOkhttpInterceptor(networkInspector, true)
    }
}

