package plugins.modules

import libs
import configure
import applicationExt
import org.gradle.api.Project
import plugins.BasePlugin


class AppPlugin : BasePlugin() {

    override val implementations: List<Any>
        get() = listOf(
            libs.core.ktx,
            libs.appcompat,
            libs.material,
            libs.constraintlayout,
        )

    override val pluginList: List<Any> get() = listOf(
        libs.plugins.com.android.application,
        libs.plugins.org.jetbrains.kotlin.android
    )

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
        applicationExt.configure()
    }
    override fun afterApply(target: Project) {

    }

}