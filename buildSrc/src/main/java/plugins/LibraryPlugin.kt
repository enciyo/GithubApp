package plugins

import DefaultConfig
import libs
import librariesExt
import configure
import org.gradle.api.Project

class LibraryPlugin : BasePlugin(isEnableLogging = true) {

    override val pluginList: List<Any>
        get() = listOf(
            libs.plugins.com.android.library,
            libs.plugins.org.jetbrains.kotlin.android
        )

    override val implementations: List<Any>
        get() = listOf(
            libs.core.ktx
        )

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
        librariesExt.configure(
            DefaultConfig.applicationId.replace(
                DefaultConfig.appName,
                target.name
            )
        )
    }

    override fun afterApply(target: Project) {

    }


}