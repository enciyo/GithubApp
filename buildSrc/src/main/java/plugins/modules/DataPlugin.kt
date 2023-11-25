package plugins.modules

import org.gradle.api.Project
import plugins.BasePlugin
import plugins.LibraryPlugin


class DataPlugin : BasePlugin(){

    override val pluginList: List<Any>
        get() = listOf(LibraryPlugin::class.java)

    override fun beforeApply(target: Project) {
        super.beforeApply(target)
    }

    override fun afterApply(target: Project) {

    }
}