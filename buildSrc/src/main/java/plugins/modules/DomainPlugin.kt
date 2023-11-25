package plugins.modules

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import plugins.BasePlugin
import plugins.LibraryPlugin


class DomainPlugin : BasePlugin() {

    override val pluginList: List<Any>
        get() = listOf(LibraryPlugin::class.java)

    override fun afterApply(target: Project) {

    }


}