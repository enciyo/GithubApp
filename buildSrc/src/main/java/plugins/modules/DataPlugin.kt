package plugins.modules

import libs
import org.gradle.api.Project
import plugins.BasePlugin
import plugins.LibraryPlugin


class DataPlugin : BasePlugin(){

    override val pluginList: List<Any>
        get() = listOf(LibraryPlugin::class.java)

    override val implementations: List<Any> get() = listOf(
        libs.retrofit,
        libs.retrofit.converter.moshi,
        libs.moshi.kotlin.nullsafe
    )

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
    }

    override fun afterApply(target: Project) {

    }
}