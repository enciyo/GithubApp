package plugins.common

import libs
import org.gradle.api.Project
import plugins.BasePlugin


class DaggerHiltPlugin  : BasePlugin(){

    override val implementations: List<Any> get() = listOf(
        libs.hilt.android,
    )

    override val kapts: List<Any> get() =  listOf(
        libs.hilt.compiler
    )

    override val pluginList: List<Any> get() = listOf(
        libs.plugins.kotlin.kapt,
        libs.plugins.dagger.hilt,
    )

    override fun afterApply(target: Project) {

    }
}