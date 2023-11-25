package plugins.modules

import org.gradle.api.Project
import plugins.BasePlugin
import plugins.LibraryPlugin


class SharedPlugin : BasePlugin() {
    override val pluginList: List<Any>
        get() = listOf(LibraryPlugin::class.java)

    override fun afterApply(target: Project) {

    }


}