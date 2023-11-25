package plugins

import applicationExt
import implementation
import kapt
import libs
import applyPlugins
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

abstract class BasePlugin(private val isEnableLogging: Boolean = true) : Plugin<Project> {
    open val implementations: List<Any> get() = listOf()
    open val kapts: List<Any> get() = listOf()
    open val pluginList: List<Any> get() = listOf()

    lateinit var target : Project

    @Deprecated("Don't use")
    override fun apply(target: Project) {
        this.target = target
        //Apply plugins

        pluginList.forEach(target::applyPlugins)

        beforeApply(target)
        //Apply dependencies
        target.dependencies {
            implementations.forEach(::implementation)
            kapts.forEach(::kapt)
        }
        afterApply(target)

        if (isEnableLogging)
            println("-> Applied ${this.javaClass.name} to ${target.name}")
    }

    open fun beforeApply(target: Project) {}
    abstract fun afterApply(target: Project)



}
