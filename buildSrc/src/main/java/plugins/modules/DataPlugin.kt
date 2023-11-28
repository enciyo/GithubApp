package plugins.modules

import libs
import implementationProject
import debugImplementation
import releaseImplementation
import org.gradle.api.Project
import plugins.BasePlugin
import plugins.LibraryPlugin


class DataPlugin : BasePlugin(){

    override val pluginList: List<Any>
        get() = listOf(
            LibraryPlugin::class.java,
            libs.plugins.kotlin.kapt
        )

    override val implementations: List<Any> get() = listOf(
        libs.retrofit,
        libs.retrofit.converter.moshi,
        libs.moshi.kotlin.nullsafe,
        libs.androidx.room.runtime,
        libs.androidx.room.ktx
    )

    override val kapts: List<Any> get() = listOf(
        libs.androidx.room.compiler
    )

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
    }

    override fun afterApply(target: Project) {
        implementationProject(":shared")
        implementationProject(":domain")
        debugImplementation(libs.flipper)
        debugImplementation(libs.soloader)
        debugImplementation(libs.flipper.network.plugin)
        releaseImplementation(libs.flipper.noop)

    }

}