package plugins.modules

import libs
import configure
import applicationExt
import plugins.common.DaggerHiltPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
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
        libs.plugins.org.jetbrains.kotlin.android,
        DaggerHiltPlugin::class.java
    )

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
        applicationExt.configure()
    }
    override fun afterApply(target: Project) {
        
    }

}