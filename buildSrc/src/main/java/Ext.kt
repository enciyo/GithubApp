import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependency
import plugins.BasePlugin
import kotlin.reflect.KClass

internal val BasePlugin.libs: LibrariesForLibs
    get() = target.extensions.getByType<LibrariesForLibs>()

internal val BasePlugin.applicationExt: ApplicationExtension
    get() = target.extensions.getByType<ApplicationExtension>()

internal  val BasePlugin.librariesExt: LibraryExtension
    get() = target.extensions.getByType<LibraryExtension>()

internal  fun Project.applyPlugins(any: Any) {
    when (any) {
        is String -> pluginManager.apply(any)
        is Class<*> -> pluginManager.apply(any)
        is KClass<*> -> pluginManager.apply(any)
        is Provider<*> -> pluginManager.apply((any.get() as PluginDependency).pluginId)
        else -> throw IllegalStateException("Not handled " + any.javaClass.name)
    }
}

internal fun BasePlugin.implementation(dependencyNotation: Any) {
    target.dependencies.add("implementation", dependencyNotation)
}

internal fun BasePlugin.debugImplementation(dependencyNotation: Any) {
    target.dependencies.add("debugImplementation", dependencyNotation)
}

internal fun BasePlugin.releaseImplementation(dependencyNotation: Any) {
    target.dependencies.add("releaseImplementation", dependencyNotation)
}

internal fun BasePlugin.testImplementation(dependencyNotation: Any) {
    target.dependencies.add("testImplementation", dependencyNotation)
}

internal fun BasePlugin.androidTestImplementation(dependencyNotation: Any) {
    target.dependencies.add("androidTestImplementation", dependencyNotation)
}

internal fun BasePlugin.kapt(dependencyNotation: Any) {
    target.dependencies.add("kapt", dependencyNotation)
}

internal fun BasePlugin.implementationProject(dependencyNotation: Any){
    target.dependencies.add("implementation", target.dependencies.project(mapOf("path" to ":$dependencyNotation")))
}

